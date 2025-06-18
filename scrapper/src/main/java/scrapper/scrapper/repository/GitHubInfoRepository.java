package scrapper.scrapper.repository;

import scrapper.scrapper.entity.GitHubInfo;

public interface GitHubInfoRepository {
    void save(GitHubInfo gitHubInfo);

    GitHubInfo find(Long linkId);

    void update(Long id, int lastCommitCount, int lastBranchCount);
}
