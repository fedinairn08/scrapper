package scrapper.scrapper.repository.jdbc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scrapper.scrapper.entity.Chat;
import scrapper.scrapper.entity.Link;
import scrapper.scrapper.repository.ChatRepository;
import scrapper.scrapper.repository.LinkRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JdbcChatAndLinkTests {
    private static Chat testChat;
    private static Link testLink;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private LinkRepository linkRepository;

    @BeforeAll
    public static void setTestData() throws URISyntaxException {
        testChat = new Chat()
                .setChatId(1L);

        testLink = new Link()
                .setUrl(new URI("http://localhost:8080"))
                .setChat(testChat)
                .setLastUpdate(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    @Transactional
    @Rollback
    public void cascadeDeleteChatAndLinkTest() {
        chatRepository.save(testChat);
        linkRepository.save(testLink);
        linkRepository.save(testLink);

        Optional<Chat> savingChat = chatRepository.findByChatId(testChat.getChatId());

        assertTrue(savingChat.isPresent());
        assertEquals(savingChat.get().getLinks().size(), 2);

        chatRepository.remove(savingChat.get().getId());

        List<Link> links = linkRepository.findAll();

        assertTrue(links.isEmpty());
    }
}
