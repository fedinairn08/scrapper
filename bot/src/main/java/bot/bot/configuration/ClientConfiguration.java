package bot.bot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfiguration {
    @Value("${baseUrl.scrapper}")
    private String scrapperBaseUrl;

    @Bean("scrapperRestClient")
    public RestClient scrapperClient() {
        return RestClient.builder()
                .baseUrl(scrapperBaseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
