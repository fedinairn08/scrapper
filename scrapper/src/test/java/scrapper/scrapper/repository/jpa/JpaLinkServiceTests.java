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
import scrapper.scrapper.entity.Link;
import scrapper.scrapper.environment.IntegrationEnvironment;
import scrapper.scrapper.service.ChatService;
import scrapper.scrapper.service.LinkService;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(excludeAutoConfiguration = LiquibaseAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(IntegrationEnvironment.JpaIntegrationEnvironmentConfiguration.class)
public class JpaLinkServiceTests {

    @Autowired
    private LinkService jpaLinkService;

    @Autowired
    private ChatService jpaChatService;

    private static Link testLink;

    private static Chat testChat;

    @BeforeEach
    public void setTestLink() throws URISyntaxException {
        testLink = new Link()
                .setUrl(new URI("https://github.com/fedinairn08/Translator-Service"))
                .setLastUpdate(new Timestamp(400000L));
        testChat = new Chat()
                .setId(1L)
                .setChatId(2L);
    }

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        jpaChatService.register(testChat.getChatId());
        Link link = jpaLinkService.add(testChat.getChatId(), testLink.getUrl());
        assertNotNull(link.getId());
    }
}
