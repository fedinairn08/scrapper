package scrapper.scrapper.service;

import linkparser.linkparser.model.LinkParserResult;
import linkparser.linkparser.service.LinkParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import scrapper.scrapper.client.BotClient;
import scrapper.scrapper.dto.request.LinkUpdateRequest;
import scrapper.scrapper.entity.Link;
import scrapper.scrapper.service.api.ApiService;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinkUpdaterScheduler {

    private final LinkService linkService;

    private final LinkParser linkParser;

    private final ApiService apiService;

    private final BotClient botClient;

    @Value("${scheduler.update}")
    private Integer timeLinkUpdate;

    @Scheduled(fixedDelayString = "#{@scheduler.interval().toMillis()}")
    public void update() {
        List<Link> links = linkService.findAllOutdatedLinks(new Timestamp(System.currentTimeMillis() - timeLinkUpdate));
        if (links != null) {
            updateLinks(links);
        }
    }

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
            linkService.updateLastUpdate(link.getId(), new Timestamp(System.currentTimeMillis()));
        }
    }

    public void sendLinkUpdate(LinkUpdateRequest updateRequest) {
        botClient.updateLink(updateRequest);
    }
}
