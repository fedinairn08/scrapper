package scrapper.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import scrapper.scrapper.dto.Scheduler;
import scrapper.scrapper.enums.AccessType;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
        @NotNull String test,
        Scheduler scheduler,
        AccessType databaseAccessType,
        Boolean useQueue
) {
}
