package scrapper.scrapper.configuration;

import linkparser.linkparser.service.LinkParseService;
import linkparser.linkparser.service.ParserConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import scrapper.scrapper.dto.Scheduler;

import javax.sql.DataSource;
import java.time.Duration;

@Configuration
@Import({LinkParseService.class, ParserConfiguration.class})
public class ApplicationConfiguration {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Bean
    public Scheduler scheduler(@Value("${app.scheduler.interval}") Duration interval) {
        return new Scheduler(interval);
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driver)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
