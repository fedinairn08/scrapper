package scrapper.scrapper.configuration;

import linkparser.linkparser.service.ParserConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import scrapper.scrapper.dto.Scheduler;

import javax.sql.DataSource;
import java.time.Duration;

@Configuration
@Import({ParserConfiguration.class})
public class ApplicationConfiguration {

    @Bean
    public Scheduler scheduler(@Value("${app.scheduler.interval}") Duration interval) {
        return new Scheduler(interval);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
