package scrapper.scrapper.repository.jdbcImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import scrapper.scrapper.entity.Chat;
import scrapper.scrapper.entity.GitHubInfo;
import scrapper.scrapper.entity.Link;
import scrapper.scrapper.repository.LinkRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class LinkRepositoryJdbcImpl implements LinkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Link save(Link link) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO link (url, chat, last_update) VALUES (?, ?, ?)",
                    new String[] {"id"});
            ps.setString(1, link.getUrl().toString());
            ps.setObject(2, link.getChat().getId());
            ps.setTimestamp(3, link.getLastUpdate());
            return ps;
        }, keyHolder);

        link.setId((long) keyHolder.getKey());
        return link;
    }

    @Override
    public void remove(Long linkId) {
        jdbcTemplate.update("DELETE FROM link WHERE id = ?", linkId);
    }

    @Override
    public List<Link> findAll() {
        String sql = "SELECT c.id AS chat_id, c.chat_id as chat_tg_id, l.id as link_id, l.url, l.last_update " +
                "FROM chat as c RIGHT JOIN link as l ON c.id = l.chat";

        return jdbcTemplate.query(sql, this::mapRowToLinkWithChat);
    }

    @Override
    public void updateLastUpdate(Long linkId, Timestamp timeUpdate) {
        String sql = "UPDATE link SET last_update = ? WHERE id = ?";
        jdbcTemplate.update(sql, timeUpdate, linkId);
    }

    @Override
    public List<Link> findAllOutdatedLinks(Timestamp timestamp) {
        String sql = "SELECT c.id AS chat_id, c.chat_id as chat_tg_id, l.id as link_id, l.url, l.last_update " +
                "FROM chat as c RIGHT JOIN link as l ON c.id = l.chat WHERE l.last_update < ?";

        return jdbcTemplate.query(sql, this::mapRowToLinkWithChat, timestamp);
    }

    @Override
    public void saveGitHubInfo(GitHubInfo gitHubInfo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO gitHub_info (last_commit_count, last_branch_count, link_id) VALUES (?, ?, ?)",
                    new String[] {"id"});
            ps.setInt(1, gitHubInfo.getLastCommitCount());
            ps.setInt(2, gitHubInfo.getLastBranchCount());
            ps.setObject(3, gitHubInfo.getLink().getId());
            return ps;
        }, keyHolder);

        gitHubInfo.setId((long) keyHolder.getKey());
    }

    @Override
    public GitHubInfo findGitHubInfo(Long linkId) {
        String sql = "SELECT * FROM github_info WHERE link_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowToGitHubInfo, linkId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateGitHubInfo(Long id, int lastCommitCount, int lastBranchCount) {
        String sql = "UPDATE github_info SET last_commit_count = ?, last_branch_count = ? WHERE id = ?";
        jdbcTemplate.update(sql, lastCommitCount, lastBranchCount, id);
    }

    private Link mapRowToLinkWithChat(ResultSet rs, int rowNum) throws SQLException {
        try {
            String rawUrl = rs.getString("url");
            String cleanedUrl = rawUrl != null ? rawUrl.replaceAll("^\"|\"$", "") : "";

            Long chatId = rs.getObject("chat_id", Long.class);
            Long chatTgId = rs.getObject("chat_tg_id", Long.class);

            Chat chat = null;
            if (chatId != null && chatTgId != null) {
                chat = new Chat(chatId, chatTgId, new ArrayList<>());
            }

            return new Link(
                    rs.getObject("link_id", Long.class),
                    new URI(cleanedUrl),
                    chat,
                    rs.getTimestamp("last_update")
            );
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URL format in database: " + rs.getString("url"), e);
        }
    }

    private GitHubInfo mapRowToGitHubInfo(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getObject("id", Long.class);
        Integer lastCommitCount = rs.getObject("last_commit_count", Integer.class);
        Integer lastBranchCount = rs.getObject("last_branch_count", Integer.class);
        Long linkId = rs.getObject("link_id", Long.class);

        Link link = jdbcTemplate.queryForObject(
                "SELECT c.id AS chat_id, c.chat_id as chat_tg_id, l.id as link_id, l.url, l.last_update " +
                "FROM chat as c RIGHT JOIN link as l ON c.id = l.chat WHERE l.id = ?",
                this::mapRowToLinkWithChat,
                linkId
        );

        return new GitHubInfo(id, lastCommitCount, lastBranchCount, link);
    }
}
