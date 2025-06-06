package scrapper.scrapper.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scrapper.scrapper.client.GitHubClient;
import scrapper.scrapper.dto.LinkData;
import scrapper.scrapper.dto.gitHub.LinkDataGitHub;
import scrapper.scrapper.dto.response.GitHubRepositoryResponse;
import scrapper.scrapper.enums.Site;

@Service
@RequiredArgsConstructor
public class GitHubApiService extends ApiService {

    private final GitHubClient gitHubClient;

    @Override
    public String checkUpdate(LinkData linkData) {
        if (linkData.getSite().equals(Site.GITHUB)) {
            LinkDataGitHub linkDataGitHub = (LinkDataGitHub) linkData;
            GitHubRepositoryResponse response = gitHubClient.getGitHubRepositoryInfo(
                    linkDataGitHub.getUser(),
                    linkDataGitHub.getRepository()
            );
            return "Обновление [репозитория](" +
                    linkDataGitHub.getUrl() +
                    ")\n" +
                    "Последнее обновление: " +
                    response.updated_at().toString() +
                    "\n" +
                    "Последний push: " +
                    response.pushed_at().toString() +
                    "\n";
        } else if (nextService != null) {
            return nextService.checkUpdate(linkData);
        } else {
            return null;
        }
    }
}
