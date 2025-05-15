package scrapper.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Value("${baseUrl.github}")
    private String githubBaseUrl;

    @Value("${baseUrl.stackOverflow}")
    private String stackoverflowBaseUrl;

    @Bean("gitHubWebClient")
    public WebClient gitHubClient() {
        return WebClient.builder()
                .baseUrl(githubBaseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean("stackOverflowWebClient")
    public WebClient stackOverflowClient() {
        return WebClient.builder()
                .baseUrl(stackoverflowBaseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
