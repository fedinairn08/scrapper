package scrapper.scrapper.repository.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scrapper.scrapper.entity.Chat;
import scrapper.scrapper.environment.IntegrationEnvironment;
import scrapper.scrapper.service.ChatService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JpaChatServiceTests extends IntegrationEnvironment {
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
