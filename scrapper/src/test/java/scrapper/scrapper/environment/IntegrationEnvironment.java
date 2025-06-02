package scrapper.scrapper.environment;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.DirectoryResourceAccessor;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

public abstract class IntegrationEnvironment {
    private static final String DB_NAME = "scrapper";

    private static final String DB_USER = "root";

    private static final String DB_PASSWORD = "root";

    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
                .withDatabaseName(DB_NAME)
                .withUsername(DB_USER)
                .withPassword(DB_PASSWORD);

        POSTGRE_SQL_CONTAINER.start();

        applyMigrations();
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
}
