package scrapper.scrapper.configuration.access;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import scrapper.scrapper.repository.ChatRepository;
import scrapper.scrapper.repository.GitHubInfoRepository;
import scrapper.scrapper.repository.LinkRepository;
import scrapper.scrapper.repository.jdbcImpl.ChatRepositoryJdbcImpl;
import scrapper.scrapper.repository.jdbcImpl.GitHubInfoRepositoryJdbcImpl;
import scrapper.scrapper.repository.jdbcImpl.LinkRepositoryJdbcImpl;
import scrapper.scrapper.service.ChatService;
import scrapper.scrapper.service.GitHubInfoService;
import scrapper.scrapper.service.LinkService;
import scrapper.scrapper.service.jdbcImpl.JdbcChatService;
import scrapper.scrapper.service.jdbcImpl.JdbcGitHubInfoService;
import scrapper.scrapper.service.jdbcImpl.JdbcLinkService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {

    @Bean
    public LinkRepository linkRepository(JdbcTemplate jdbcTemplate) {
        return new LinkRepositoryJdbcImpl(jdbcTemplate);
    }

    @Bean
    public ChatRepository chatRepository(JdbcTemplate jdbcTemplate) {
        return new ChatRepositoryJdbcImpl(jdbcTemplate);
    }

    @Bean
    public GitHubInfoRepository gitHubInfoRepository(JdbcTemplate jdbcTemplate) {
        return new GitHubInfoRepositoryJdbcImpl(jdbcTemplate);
    }

    @Bean
    public ChatService chatService(ChatRepository chatRepository) {
        return new JdbcChatService(chatRepository);
    }

    @Bean
    public LinkService linkService(LinkRepository linkRepository,
                                   ChatService chatService) {
        return new JdbcLinkService(linkRepository, chatService);
    }

    @Bean
    public GitHubInfoService gitHubInfoService(GitHubInfoRepository gitHubInfoRepository) {
        return new JdbcGitHubInfoService(gitHubInfoRepository);
    }
}
