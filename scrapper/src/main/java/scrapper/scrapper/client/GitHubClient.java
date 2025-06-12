package scrapper.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import scrapper.scrapper.dto.response.GitHubRepositoryResponse;

@Component
@RequiredArgsConstructor
public class GitHubClient {

    private final RestClient gitHubRestClient;

    public GitHubRepositoryResponse getGitHubRepositoryInfo(String username, String repositoryName) {
        return gitHubRestClient.get()
                .uri("/repos/{username}/{repositoryName}", username, repositoryName)
                .retrieve()
                .body(GitHubRepositoryResponse.class);
    }
}
