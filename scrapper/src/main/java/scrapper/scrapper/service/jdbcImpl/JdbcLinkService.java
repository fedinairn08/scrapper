package scrapper.scrapper.service.jdbcImpl;

import lombok.RequiredArgsConstructor;
import scrapper.scrapper.entity.Chat;
import scrapper.scrapper.entity.Link;
import scrapper.scrapper.repository.LinkRepository;
import scrapper.scrapper.service.ChatService;
import scrapper.scrapper.service.LinkService;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;

    private final ChatService chatService;

    @Override
    public Link add(final long tgChatId, final URI url) {
        return linkRepository.save(
                new Link().
                setUrl(url).
                setChat(chatService.getByChatId(tgChatId).get())
                .setLastUpdate(new Timestamp(System.currentTimeMillis())));
    }

    @Override
    public void remove(final long tgChatId, final URI url) {
        Optional<Link> resultLink = chatService.getByChatId(tgChatId).get().getLinks()
                .stream()
                .filter(link -> url.equals(link.getUrl()))
                .findFirst();

        if (resultLink.isPresent()) {
            Link link = resultLink.get();
            linkRepository.remove(link.getId());
        } else {
            throw new IllegalArgumentException("Link not found");
        }
    }

    @Override
    public List<Link> listAll(final long tgChatId) {
        Optional<Chat> chat = chatService.getByChatId(tgChatId);

        if (!chat.get().getLinks().isEmpty()) {
            return chat.get().getLinks();
        } else {
            throw new RuntimeException("Links not found");
        }
    }

    @Override
    public void updateLastUpdate(final Long linkId, final Timestamp timeUpdate) {
        linkRepository.updateLastUpdate(linkId, timeUpdate);
    }

    @Override
    public List<Link> findAllOutdatedLinks(final Timestamp timestamp) {
        return linkRepository.findAllOutdatedLinks(timestamp);
    }

    @Override
    public void deleteAllByChat_ChatId(final Long chatId) {
        linkRepository.deleteAllByChat_ChatId(chatId);
    }
}
