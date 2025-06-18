package scrapper.scrapper.service;

import scrapper.scrapper.entity.GitHubInfo;
import scrapper.scrapper.entity.Link;

public interface GitHubInfoService {
    void add(Link link);

    GitHubInfo find(Long linkId);

    void update(Long id, int lastCommitCount, int lastBranchCount);
}
