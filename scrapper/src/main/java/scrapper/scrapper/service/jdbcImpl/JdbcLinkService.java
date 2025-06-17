package scrapper.scrapper.service.jdbcImpl;

import linkparser.linkparser.model.LinkParserResult;
import linkparser.linkparser.service.LinkParser;
import lombok.RequiredArgsConstructor;
import scrapper.scrapper.client.BotClient;
import scrapper.scrapper.dto.request.LinkUpdateRequest;
import scrapper.scrapper.entity.Chat;
import scrapper.scrapper.entity.GitHubInfo;
import scrapper.scrapper.entity.Link;
import scrapper.scrapper.repository.LinkRepository;
import scrapper.scrapper.service.ChatService;
import scrapper.scrapper.service.LinkService;
import scrapper.scrapper.service.api.ApiService;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;

    private final ChatService chatService;

    private final LinkParser linkParser;

    private final ApiService apiService;

    private final BotClient botClient;

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

    @Override
    public void updateLastUpdate(Long linkId, Timestamp timeUpdate) {
        linkRepository.updateLastUpdate(linkId, timeUpdate);
    }

    @Override
    public List<Link> findAllOutdatedLinks(Timestamp timestamp) {
        return linkRepository.findAllOutdatedLinks(timestamp);
    }

    @Override
    public void updateLinks(List<Link> links) {
        for (Link link: links) {
            LinkParserResult linkParserResult = linkParser.parseUrl(link.getUrl());
            String description = apiService.checkUpdate(linkParserResult, link);
            sendLinkUpdate(new LinkUpdateRequest(
                    link.getId(),
                    link.getUrl(),
                    description,
                    List.of(link.getChat().getChatId())
            ));
            updateLastUpdate(link.getId(), new Timestamp(System.currentTimeMillis()));
        }
    }

    @Override
    public void sendLinkUpdate(LinkUpdateRequest updateRequest) {
        botClient.updateLink(updateRequest);
    }

    @Override
    public void addGitHubInfo(Link link) {
        GitHubInfo gitHubInfo = new GitHubInfo();
        gitHubInfo.setLink(link);
        linkRepository.saveGitHubInfo(gitHubInfo);
    }
}
