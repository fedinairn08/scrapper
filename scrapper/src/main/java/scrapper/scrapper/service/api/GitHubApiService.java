package scrapper.scrapper.service.api;

import linkparser.linkparser.model.GitHubResult;
import linkparser.linkparser.model.LinkParserResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scrapper.scrapper.client.GitHubClient;
import scrapper.scrapper.dto.response.GitHubRepositoryResponse;
import scrapper.scrapper.entity.GitHubInfo;
import scrapper.scrapper.entity.Link;
import scrapper.scrapper.repository.LinkRepository;

@Service
@RequiredArgsConstructor
public class GitHubApiService extends ApiService {

    private final GitHubClient gitHubClient;

    private final LinkRepository linkRepository;

    @Override
    public String checkUpdate(LinkParserResult linkParserResult, Link link) {
        if (linkParserResult instanceof GitHubResult gitHubResult) {
            GitHubRepositoryResponse response = gitHubClient.getGitHubRepositoryInfo(
                    gitHubResult.user(),
                    gitHubResult.repo()
            );

            int commitCount = gitHubClient.getCommitCount(gitHubResult.user(), gitHubResult.repo());

//            int branchCount = gitHubClient.getBranchCount(gitHubResult.user(), gitHubResult.repo());

            GitHubInfo gitHubInfo = linkRepository.findGitHubInfo(link.getId());

            if (gitHubInfo.getLastCommitCount() == 0 && gitHubInfo.getLastBranchCount() == 0) {
                gitHubInfo.setLastCommitCount(commitCount);
                linkRepository.updateGitHubInfo(gitHubInfo.getId(), commitCount, 0);

//                gitHubInfo.setLastBranchCount(branchCount);
            } else {
                commitCount = commitCount - gitHubInfo.getLastCommitCount();
//                branchCount = branchCount - gitHubInfo.getLastBranchCount();
            }

            return "Обновление [репозитория](" +
                    gitHubResult.url() +
                    ")\n" +
                    "Последнее обновление: " +
                    response.updated_at().toString() +
                    "\n" +
                    "Последний push: " +
                    response.pushed_at().toString() +
                    "\n" +
                    "Количество новых коммитов: " +
                    commitCount +
                    "\n";
//                    "Количество новых веток: " +
//                    branchCount +
//                    "\n";
        } else if (nextService != null) {
            return nextService.checkUpdate(linkParserResult, link);
        } else {
            return null;
        }
    }
}
