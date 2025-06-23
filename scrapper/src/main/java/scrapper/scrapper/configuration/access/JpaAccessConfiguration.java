package scrapper.scrapper.configuration.access;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scrapper.scrapper.repository.jpa.JpaChatRepository;
import scrapper.scrapper.repository.jpa.JpaGitHubInfoRepository;
import scrapper.scrapper.repository.jpa.JpaLinkRepository;
import scrapper.scrapper.service.ChatService;
import scrapper.scrapper.service.GitHubInfoService;
import scrapper.scrapper.service.LinkService;
import scrapper.scrapper.service.jpaImpl.JpaChatService;
import scrapper.scrapper.service.jpaImpl.JpaGitHubInfoService;
import scrapper.scrapper.service.jpaImpl.JpaLinkService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {

    @Bean
    public ChatService chatService(final JpaChatRepository jpaChatRepository, final LinkService linkService) {
        return new JpaChatService(jpaChatRepository, linkService);
    }

    @Bean
    public LinkService linkService(final JpaLinkRepository jpaLinkRepository,
                                   final JpaGitHubInfoRepository jpaGitHubInfoRepository,
                                   final JpaChatRepository jpaChatRepository) {
        return new JpaLinkService(jpaLinkRepository, jpaGitHubInfoRepository, jpaChatRepository);
    }

    @Bean
    public GitHubInfoService gitHubInfoService(final JpaGitHubInfoRepository jpaGitHubInfoRepository) {
        return new JpaGitHubInfoService(jpaGitHubInfoRepository);
    }
}
