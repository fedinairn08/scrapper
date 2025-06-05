package scrapper.scrapper.service.jdbcImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scrapper.scrapper.entity.Chat;
import scrapper.scrapper.entity.Link;
import scrapper.scrapper.repository.LinkRepository;
import scrapper.scrapper.service.ChatService;
import scrapper.scrapper.service.LinkService;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;

    private final ChatService chatService;

    @Override
    public Link add(long tgChatId, URI url) {
        Link link = new Link();
        link.setUrl(url);
        link.setChat(chatService.getByChatId(tgChatId).get());
        link.setLastUpdate(new Timestamp(System.currentTimeMillis()));

        return linkRepository.save(link);
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        Optional<Link> resultLink = chatService.getByChatId(tgChatId).get().getLinks()
                .stream()
                .filter(link -> url.equals(link.getUrl()))
                .findFirst();

        if (resultLink.isPresent()) {
            Link link = resultLink.get();
            linkRepository.remove(link.getId());
            return link;
        } else {
            throw new IllegalArgumentException("Link not found");
        }
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        Optional<Chat> chat = chatService.getByChatId(tgChatId);

        if (!chat.get().getLinks().isEmpty()) {
            return chat.get().getLinks();
        } else {
            throw new RuntimeException("Links not found");
        }
    }
}
