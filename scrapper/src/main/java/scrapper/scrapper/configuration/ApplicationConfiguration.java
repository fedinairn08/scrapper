package scrapper.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scrapper.scrapper.dto.Scheduler;

import java.time.Duration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public Scheduler scheduler(@Value("${app.scheduler.interval}") Duration interval) {
        return new Scheduler(interval);
    }
}
