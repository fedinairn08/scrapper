package scrapper.scrapper.environment;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.DirectoryResourceAccessor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import scrapper.scrapper.repository.jpa.JpaChatRepository;
import scrapper.scrapper.repository.jpa.JpaGitHubInfoRepository;
import scrapper.scrapper.repository.jpa.JpaLinkRepository;
import scrapper.scrapper.service.ChatService;
import scrapper.scrapper.service.LinkService;
import scrapper.scrapper.service.jpaImpl.JpaChatService;
import scrapper.scrapper.service.jpaImpl.JpaLinkService;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

@ContextConfiguration(classes = IntegrationEnvironment.IntegrationEnvironmentConfiguration.class)
public abstract class IntegrationEnvironment {
    private static final String DB_NAME = "scrapper";

    private static final String DB_USER = "root";

    private static final String DB_PASSWORD = "root";

    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;

    private static final DataSource TEST_DATA_SOURCE;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
                .withDatabaseName(DB_NAME)
                .withUsername(DB_USER)
                .withPassword(DB_PASSWORD);

        POSTGRE_SQL_CONTAINER.start();

        applyMigrations();

        TEST_DATA_SOURCE = DataSourceBuilder.create()
                .url(POSTGRE_SQL_CONTAINER.getJdbcUrl())
                .username(POSTGRE_SQL_CONTAINER.getUsername())
                .password(POSTGRE_SQL_CONTAINER.getPassword())
                .build();
    }

    public static PostgreSQLContainer<?> getPostgreSQLContainer() {
        return POSTGRE_SQL_CONTAINER;
    }

    private static void applyMigrations() {
        try (Connection connection = DriverManager.getConnection(
                POSTGRE_SQL_CONTAINER.getJdbcUrl(),
                POSTGRE_SQL_CONTAINER.getUsername(),
                POSTGRE_SQL_CONTAINER.getPassword()
        )) {
            Path changeLogPath = new File(".").toPath().toAbsolutePath().getParent().getParent()
                    .resolve("migrations");

            Liquibase liquibase = new Liquibase(
                    "master.xml",
                    new DirectoryResourceAccessor(changeLogPath),
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection)));

            liquibase.update(new Contexts(), new LabelExpression());
        } catch (Exception e) {
            throw new RuntimeException("Не удалось запустить миграции Liquibase", e);
        }
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
    }

    @Configuration
    public static class JpaIntegrationEnvironmentConfiguration {
        @Bean
        public DataSource dataSource() {
            return TEST_DATA_SOURCE;
        }

        @Bean
        public ChatService jpaChatService(JpaChatRepository jpaChatRepository, LinkService linkService) {
            return new JpaChatService(jpaChatRepository, linkService);
        }

        @Bean
        public LinkService jpaLinkService(
                JpaLinkRepository jpaLinkRepository,
                JpaGitHubInfoRepository jpaGitHubInfoRepository,
                JpaChatRepository jpaChatRepository
        ) {
            return new JpaLinkService(jpaLinkRepository, jpaGitHubInfoRepository, jpaChatRepository);
        }
    }

    @Configuration
    public static class IntegrationEnvironmentConfiguration {

        @Bean
        public DataSource dataSource() {
            return TEST_DATA_SOURCE;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new JdbcTransactionManager(dataSource);
        }
    }
}
