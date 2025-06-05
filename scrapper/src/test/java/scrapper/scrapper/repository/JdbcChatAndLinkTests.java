package scrapper.scrapper.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scrapper.scrapper.entity.Chat;
import scrapper.scrapper.entity.Link;

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
        testChat = new Chat();
        testChat.setChatId(1L);

        testLink = new Link();
        testLink.setUrl(new URI("http://localhost:8080"));
        testLink.setChat(testChat);
        testLink.setLastUpdate(new Timestamp(System.currentTimeMillis()));
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
