package scrapper.scrapper.repository.jdbc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scrapper.scrapper.entity.Chat;
import scrapper.scrapper.environment.IntegrationEnvironment;
import scrapper.scrapper.repository.ChatRepository;
import scrapper.scrapper.repository.jdbcImpl.ChatRepositoryJdbcImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {
        IntegrationEnvironment.IntegrationEnvironmentConfiguration.class,
        ChatRepositoryJdbcImpl.class,
})
public class JdbcChatTests {

    private static Chat testChat;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void cleanDatabase() {
        jdbcTemplate.update("DELETE FROM link");
        jdbcTemplate.update("DELETE FROM chat");
    }

    @BeforeAll
    public static void setTestChat() {
        testChat = new Chat()
                .setChatId(1L);
    }

    @Transactional
    @Rollback
    @Test
    public void saveTest() {
        Chat chat = chatRepository.save(testChat);

        assertNotNull(chat);
        assertThat(chat.getChatId()).isEqualTo(testChat.getChatId());
    }

    @Transactional
    @Rollback
    @Test
    public void removeTest() {
        Chat chat = chatRepository.save(testChat);
        chatRepository.remove(chat.getId());

        assertEquals(0, chatRepository.findAll().size());
    }

    @Transactional
    @Rollback
    @Test
    public void findAllTest() {
        chatRepository.save(testChat);
        Chat secondChat = new Chat()
                .setChatId(2L);
        chatRepository.save(secondChat);

        assertEquals(2, chatRepository.findAll().size());
    }

    @Transactional
    @Rollback
    @Test
    public void findByChatIdTest() {
        chatRepository.save(testChat);
        Optional<Chat> chat = chatRepository.findByChatId(1L);
        assertNotNull(chat);
    }
}
