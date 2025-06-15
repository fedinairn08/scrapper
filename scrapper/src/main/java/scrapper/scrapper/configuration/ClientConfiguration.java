package scrapper.scrapper.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {

    private final ClientConfig clientConfig;

    @Bean("gitHubRestClient")
    public RestClient gitHubClient() {
        return RestClient.builder()
                .baseUrl(clientConfig.githubBaseUrl())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean("stackOverflowRestClient")
    public RestClient stackOverflowClient() {
        return RestClient.builder()
                .baseUrl(clientConfig.stackoverflowBaseUrl())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean("botRestClient")
    public RestClient botClient() {
        return RestClient.builder()
                .baseUrl(clientConfig.botBaseUrl())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
