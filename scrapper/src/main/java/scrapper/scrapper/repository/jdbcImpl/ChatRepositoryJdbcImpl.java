package scrapper.scrapper.repository.jdbcImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import scrapper.scrapper.entity.Chat;
import scrapper.scrapper.entity.Link;
import scrapper.scrapper.repository.ChatRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class ChatRepositoryJdbcImpl implements ChatRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Chat save(Chat chat) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO chat (chat_id) VALUES (?)",
                    new String[] {"id"});
            ps.setLong(1, chat.getChatId());
            return ps;
        }, keyHolder);

        chat.setId((long) keyHolder.getKey());
        return chat;
    }

    @Override
    public void remove(Long chatId) {
        jdbcTemplate.update("DELETE FROM chat WHERE id = ?", chatId);
    }

    @Override
    public List<Chat> findAll() {
        String sql = "SELECT c.id AS chat_id, c.chat_id as chat_tg_id, l.id as link_id, l.url, l.last_update " +
                "FROM chat as c LEFT JOIN link as l ON c.id = l.chat";


        return jdbcTemplate.query(sql, this::mapRowToChatWithLinks);
    }

    private List<Chat> mapRowToChatWithLinks(ResultSet rs) throws SQLException {
        Map<Long, Chat> chatMap = new LinkedHashMap<>();

        while (rs.next()) {
            Long chatId = rs.getLong("chat_id");

            Chat chat = chatMap.computeIfAbsent(chatId, k -> {
                try {
                    return Chat.builder()
                            .id(rs.getLong("chat_id"))
                            .chatId(rs.getLong("chat_tg_id"))
                            .links(new ArrayList<>())
                            .build();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            Long linkId = rs.getObject("link_id", Long.class);
            if (linkId != null) {
                try {
                    chat.getLinks().add(Link.builder()
                            .id(linkId)
                            .url(new URI(rs.getString("url")))
                            .lastUpdate(rs.getTimestamp("last_update"))
                            .build());
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }

            chatMap.put(chatId, chat);
        }

        return new ArrayList<>(chatMap.values());
    }
}
