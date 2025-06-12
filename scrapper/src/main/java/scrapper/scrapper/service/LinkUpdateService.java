package scrapper.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scrapper.scrapper.client.BotClient;
import scrapper.scrapper.dto.request.LinkUpdateRequest;

@Service
@RequiredArgsConstructor
public class LinkUpdateService {

    private final BotClient botClient;

    public void sendLinkUpdate(LinkUpdateRequest updateRequest) {
        botClient.updateLink(updateRequest);
    }
}
