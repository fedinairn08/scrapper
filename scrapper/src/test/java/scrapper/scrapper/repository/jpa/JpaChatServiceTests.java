package scrapper.scrapper.repository.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scrapper.scrapper.entity.Chat;
import scrapper.scrapper.environment.IntegrationEnvironment;
import scrapper.scrapper.service.ChatService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(excludeAutoConfiguration = LiquibaseAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(IntegrationEnvironment.JpaIntegrationEnvironmentConfiguration.class)
public class JpaChatServiceTests {
    private static Chat testChat;

    @Autowired
    private ChatService jpaChatService;

    @BeforeEach
    public void setTestChat() {
        testChat = new Chat()
                .setChatId(1L);
    }

    @Test
    @Transactional
    @Rollback
    public void registerTest() {
        jpaChatService.register(testChat.getChatId());
        Optional<Chat> chat = jpaChatService.getByChatId(testChat.getChatId());
        assertNotNull(jpaChatService.getByChatId(chat.get().getChatId()));
    }
}
