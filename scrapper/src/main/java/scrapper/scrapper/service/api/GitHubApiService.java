package scrapper.scrapper.service.api;

import linkparser.linkparser.model.GitHubResult;
import linkparser.linkparser.model.LinkParserResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scrapper.scrapper.client.GitHubClient;
import scrapper.scrapper.dto.response.GitHubRepositoryResponse;

@Service
@RequiredArgsConstructor
public class GitHubApiService extends ApiService {

    private final GitHubClient gitHubClient;

    @Override
    public String checkUpdate(LinkParserResult linkParserResult) {
        if (linkParserResult instanceof GitHubResult gitHubResult) {
            GitHubRepositoryResponse response = gitHubClient.getGitHubRepositoryInfo(
                    gitHubResult.user(),
                    gitHubResult.repo()
            );
            return "Обновление [репозитория](" +
                    gitHubResult.url() +
                    ")\n" +
                    "Последнее обновление: " +
                    response.updated_at().toString() +
                    "\n" +
                    "Последний push: " +
                    response.pushed_at().toString() +
                    "\n";
        } else if (nextService != null) {
            return nextService.checkUpdate(linkParserResult);
        } else {
            return null;
        }
    }
}
