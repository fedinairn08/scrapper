package scrapper.scrapper.service.jdbcImpl;

import lombok.RequiredArgsConstructor;
import scrapper.scrapper.entity.GitHubInfo;
import scrapper.scrapper.entity.Link;
import scrapper.scrapper.repository.GitHubInfoRepository;
import scrapper.scrapper.service.GitHubInfoService;

@RequiredArgsConstructor
public class JdbcGitHubInfoService implements GitHubInfoService {

    private final GitHubInfoRepository gitHubInfoRepository;

    @Override
    public void add(Link link) {
        gitHubInfoRepository.save(new GitHubInfo().setLink(link));
    }

    @Override
    public GitHubInfo find(Long linkId) {
        return gitHubInfoRepository.find(linkId);
    }

    @Override
    public void update(Long id, int lastCommitCount, int lastBranchCount) {
        gitHubInfoRepository.update(id, lastCommitCount, lastBranchCount);
    }
}
