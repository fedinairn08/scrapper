package scrapper.scrapper.repository;

import org.junit.jupiter.api.BeforeEach;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JdbcLinkTests {

    private Link testLink;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private ChatRepository chatRepository;

    @BeforeEach
    public void setTestLink() throws URISyntaxException {
        Chat chat = new Chat();
        chat.setChatId(1L);
        chat = chatRepository.save(chat);

        testLink = new Link();
        testLink.setUrl(new URI("http://localhost:8080"));
        testLink.setChat(chat);
        testLink.setLastUpdate(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    @Transactional
    @Rollback
    public void saveTest() {
        Link link = linkRepository.save(testLink);

        assertNotNull(link);
        assertThat(link.getUrl()).isEqualTo(testLink.getUrl());
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        Link link = linkRepository.save(testLink);
        linkRepository.remove(link.getId());

        assertEquals(0, linkRepository.findAll().size());
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() throws URISyntaxException {
        linkRepository.save(testLink);

        Chat secondChat = new Chat();
        secondChat.setChatId(2L);
        chatRepository.save(secondChat);

        Link secondLink = new Link();
        secondLink.setUrl(new URI("http://localhost:8080"));
        secondLink.setChat(secondChat);
        secondLink.setLastUpdate(new Timestamp(System.currentTimeMillis()));

        linkRepository.save(secondLink);

        assertEquals(2, linkRepository.findAll().size());
    }
}
