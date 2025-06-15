package scrapper.scrapper.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "client", ignoreUnknownFields = false)
public record ClientConfig (
        String githubBaseUrl,

        String stackoverflowBaseUrl,

        String botBaseUrl
) {
}
