package scrapper.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import scrapper.scrapper.dto.response.GitHubRepositoryResponse;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class GitHubClient {

    private final RestClient gitHubRestClient;

    @Value("${api.github.token}")
    private String githubToken;

    public GitHubRepositoryResponse getGitHubRepositoryInfo(String username, String repositoryName) {
        return gitHubRestClient.get()
                .uri("/repos/{username}/{repositoryName}", username, repositoryName)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + githubToken)
                .retrieve()
                .body(GitHubRepositoryResponse.class);
    }

    public int getCommitCount(String username, String repositoryName) {
        ResponseEntity<Void> response = gitHubRestClient.get()
                .uri("/repos/{username}/{repositoryName}/commits?per_page=1", username, repositoryName)
                .retrieve()
                .toBodilessEntity();

        return extractCommitCountFromHeaders(response.getHeaders());
    }

    public int getBranchCount(String username, String repositoryName) {
        ResponseEntity<Void> response = gitHubRestClient.get()
                .uri("/repos/{username}/{repositoryName}/branches?per_page=1", username, repositoryName)
                .retrieve()
                .toBodilessEntity();

        return extractBranchCountFromHeaders(response.getHeaders());
    }

    private int extractBranchCountFromHeaders(HttpHeaders headers) {
        String linkHeader = headers.getFirst(HttpHeaders.LINK);
        if (linkHeader != null) {
            Pattern pattern = Pattern.compile("page=(\\d+)>; rel=\"last\"");
            Matcher matcher = pattern.matcher(linkHeader);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
        }
        return 1;

    }

    private int extractCommitCountFromHeaders(HttpHeaders headers) {
        String linkHeader = headers.getFirst(HttpHeaders.LINK);
        Pattern pattern = Pattern.compile("page=(\\d+)>; rel=\"last\"");
        Matcher matcher = pattern.matcher(linkHeader);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return getCommitCountFromBody(headers);
    }

    private int getCommitCountFromBody(HttpHeaders headers) {
        String path = headers.getFirst("X-GitHub-Request-Id");

        List<?> commits = gitHubRestClient.get()
                .uri(path)
                .retrieve()
                .body(List.class);

        return commits.size();
    }
}
