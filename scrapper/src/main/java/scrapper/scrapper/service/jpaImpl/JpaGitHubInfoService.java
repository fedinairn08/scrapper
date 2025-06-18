package scrapper.scrapper.service.jpaImpl;

import lombok.RequiredArgsConstructor;
import scrapper.scrapper.entity.GitHubInfo;
import scrapper.scrapper.entity.Link;
import scrapper.scrapper.repository.jpa.JpaGitHubInfoRepository;
import scrapper.scrapper.service.GitHubInfoService;

@RequiredArgsConstructor
public class JpaGitHubInfoService implements GitHubInfoService {

    private final JpaGitHubInfoRepository jpaGitHubInfoRepository;

    @Override
    public void add(Link link) {
        jpaGitHubInfoRepository.save(new GitHubInfo().setLink(link));
    }

    @Override
    public GitHubInfo find(Long linkId) {
        return jpaGitHubInfoRepository.findByLink_Id(linkId);
    }

    @Override
    public void update(Long id, int lastCommitCount, int lastBranchCount) {
        jpaGitHubInfoRepository.save(
                jpaGitHubInfoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Link with id [" + id + "] not found"))
                        .setLastCommitCount(lastCommitCount)
                        .setLastBranchCount(lastBranchCount)
        );
    }
}
