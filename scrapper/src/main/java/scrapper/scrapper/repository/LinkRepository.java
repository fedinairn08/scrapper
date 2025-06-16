package scrapper.scrapper.repository;

import org.springframework.stereotype.Repository;
import scrapper.scrapper.entity.GitHubInfo;
import scrapper.scrapper.entity.Link;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface LinkRepository {
    Link save(Link link);

    void remove(Long linkId);

    List<Link> findAll();

    void updateLastUpdate(Long linkId, Timestamp timeUpdate);

    List<Link> findAllOutdatedLinks(Timestamp timestamp);

    void saveGitHubInfo(GitHubInfo gitHubInfo);

    GitHubInfo findGitHubInfo(Long linkId);

    void updateGitHubInfo(Long id, int lastCommitCount, int lastBranchCount);
}
