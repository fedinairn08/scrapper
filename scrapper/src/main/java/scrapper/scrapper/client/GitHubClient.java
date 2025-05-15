package scrapper.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import scrapper.scrapper.dto.response.GitHubRepositoryResponse;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class GitHubClient {
    private final WebClient gitHubWebClient;

    @Value("${default.timeout}")
    private Integer defaultTimeout;

    public GitHubRepositoryResponse getGitHubRepositoryInfo(String username, String repositoryName) {
        return gitHubWebClient.get()
                .uri("/repos/{username}/{repositoryName}", username, repositoryName)
                .retrieve()
                .bodyToMono(GitHubRepositoryResponse.class)
                .timeout(Duration.ofSeconds(defaultTimeout))
                .blockOptional()
                .orElse(null);
    }
}
