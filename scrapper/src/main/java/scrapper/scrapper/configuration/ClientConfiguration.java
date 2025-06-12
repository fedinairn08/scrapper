package scrapper.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfiguration {
    @Value("${baseUrl.github}")
    private String githubBaseUrl;

    @Value("${baseUrl.stackOverflow}")
    private String stackoverflowBaseUrl;

    @Value("${baseUrl.bot}")
    private String botBaseUrl;

    @Bean("gitHubRestClient")
    public RestClient gitHubClient() {
        return RestClient.builder()
                .baseUrl(githubBaseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean("stackOverflowRestClient")
    public RestClient stackOverflowClient() {
        return RestClient.builder()
                .baseUrl(stackoverflowBaseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean("botRestClient")
    public RestClient botClient() {
        return RestClient.builder()
                .baseUrl(botBaseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
