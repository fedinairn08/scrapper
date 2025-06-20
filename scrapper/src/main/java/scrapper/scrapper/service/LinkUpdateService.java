package scrapper.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scrapper.scrapper.client.BotClient;
import scrapper.scrapper.configuration.ApplicationConfig;
import scrapper.scrapper.dto.request.LinkUpdateRequest;

@Service
@RequiredArgsConstructor
public class LinkUpdateService {

    private final BotClient botClient;

    private final ApplicationConfig applicationConfig;

    private final ScrapperQueueProducer scrapperQueueProducer;

    public void sendLinkUpdate(LinkUpdateRequest updateRequest) {
        if (applicationConfig.useQueue()) {
            scrapperQueueProducer.send(updateRequest);
        } else {
            botClient.updateLink(updateRequest);
        }
    }
}
