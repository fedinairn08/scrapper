package scrapper.scrapper.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import scrapper.scrapper.environment.IntegrationEnvironment;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class IntegrationTests extends IntegrationEnvironment {
    private static final PostgreSQLContainer<?> container = IntegrationEnvironment.getPostgreSQLContainer();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    }

    @Test
    void postgresqlTest() {
        try (Connection conn = container.createConnection("")) {
            var stmt = conn.createStatement();
            var rs = stmt.executeQuery(
                    "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public'");
            assertThat(rs.next()).isTrue();
            assertThat(rs.getInt(1)).isGreaterThan(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Long id = 1L;
        Long chatId = 123L;
        jdbcTemplate.update("INSERT INTO chat VALUES (?, ?)", id, chatId);

        var result = jdbcTemplate.queryForList("SELECT * FROM chat");
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).get("id")).isEqualTo(id);
        assertThat(result.get(0).get("chat_id")).isEqualTo(chatId);
    }
}
