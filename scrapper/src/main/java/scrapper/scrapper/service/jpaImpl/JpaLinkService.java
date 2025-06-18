package scrapper.scrapper.service.jpaImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import scrapper.scrapper.entity.Chat;
import scrapper.scrapper.entity.Link;
import scrapper.scrapper.repository.jpa.JpaChatRepository;
import scrapper.scrapper.repository.jpa.JpaGitHubInfoRepository;
import scrapper.scrapper.repository.jpa.JpaLinkRepository;
import scrapper.scrapper.service.LinkService;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {

    private final JpaLinkRepository jpaLinkRepository;

    private final JpaGitHubInfoRepository jpaGitHubInfoRepository;

    private final JpaChatRepository jpaChatRepository;

    @Override
    public Link add(long tgChatId, URI url) {
        return jpaLinkRepository.save(
                new Link()
                .setChat(jpaChatRepository.findByChatId(tgChatId))
                .setUrl(url)
                .setLastUpdate(new Timestamp(System.currentTimeMillis())));
    }

    @Transactional
    @Override
    public void remove(long tgChatId, URI url) {
        Chat chat = jpaChatRepository.findByChatId(tgChatId);

        Link linkToRemove = chat.getLinks().stream()
                .filter(link -> url.equals(link.getUrl()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Link not found"));

        jpaGitHubInfoRepository.deleteByLink(linkToRemove);

        chat.getLinks().remove(linkToRemove);

        jpaLinkRepository.delete(linkToRemove);
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        return jpaChatRepository.findByChatId(tgChatId).getLinks();
    }

    @Override
    public void updateLastUpdate(Long linkId, Timestamp timeUpdate) {
        jpaLinkRepository.save(
                jpaLinkRepository.findById(linkId)
                        .orElseThrow(() -> new RuntimeException("Link with id [" + linkId + "] not found"))
                        .setLastUpdate(timeUpdate)
        );
    }

    @Override
    public List<Link> findAllOutdatedLinks(Timestamp timestamp) {
        return jpaLinkRepository.findAllByLastUpdateLessThan(timestamp);
    }

    @Override
    public void deleteAllByChat_ChatId(Long chatId) {
        jpaLinkRepository.deleteAllByChat_ChatId(chatId);
    }
}
