package scrapper.scrapper.repository.jdbc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scrapper.scrapper.entity.Chat;
import scrapper.scrapper.entity.Link;
import scrapper.scrapper.environment.IntegrationEnvironment;
import scrapper.scrapper.repository.ChatRepository;
import scrapper.scrapper.repository.LinkRepository;
import scrapper.scrapper.repository.jdbcImpl.ChatRepositoryJdbcImpl;
import scrapper.scrapper.repository.jdbcImpl.LinkRepositoryJdbcImpl;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {
        IntegrationEnvironment.IntegrationEnvironmentConfiguration.class,
        ChatRepositoryJdbcImpl.class,
        LinkRepositoryJdbcImpl.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JdbcChatAndLinkTests {
    private static Chat testChat;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void cleanDatabase() {
        jdbcTemplate.update("DELETE FROM link");
        jdbcTemplate.update("DELETE FROM chat");
    }

    @BeforeAll
    public static void setTestData() {
        testChat = new Chat()
                .setChatId(1L);
    }

    @Test
    @Transactional
    @Rollback
    public void cascadeDeleteChatAndLinkTest() throws URISyntaxException {
        chatRepository.save(testChat);

        Link link1 = new Link()
                .setUrl(new URI("http://localhost:8080/1"))
                .setChat(testChat)
                .setLastUpdate(new Timestamp(System.currentTimeMillis()));

        Link link2 = new Link()
                .setUrl(new URI("http://localhost:8080/2"))
                .setChat(testChat)
                .setLastUpdate(new Timestamp(System.currentTimeMillis()));

        linkRepository.save(link1);
        linkRepository.save(link2);

        Optional<Chat> savingChat = chatRepository.findByChatId(testChat.getChatId());

        assertTrue(savingChat.isPresent());
        assertEquals(savingChat.get().getLinks().size(), 2);

        chatRepository.remove(savingChat.get().getId());

        List<Link> links = linkRepository.findAll();

        assertTrue(links.isEmpty());
    }
}
