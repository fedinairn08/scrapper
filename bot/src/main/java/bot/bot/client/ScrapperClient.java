package bot.bot.client;

import bot.bot.dto.scrapper.request.AddLinkRequest;
import bot.bot.dto.scrapper.response.LinkResponse;
import bot.bot.dto.scrapper.response.ListLinksResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScrapperClient {
    private final RestClient scrapperRestClient;

    public void registerChat(long chatId) {
        scrapperRestClient.post()
                .uri("/tg-chat/" + chatId)
                .retrieve()
                .toBodilessEntity();
    }

    public void deleteChat(long chatId) {
        scrapperRestClient.delete()
                .uri("/tg-chat/" + chatId)
                .retrieve()
                .toBodilessEntity();
    }

    public Optional<ListLinksResponse> getLinks(long chatId) {
        return Optional.of(scrapperRestClient.get()
                .uri("/links")
                .header("Tg-Chat-Id", String.valueOf(chatId))
                .retrieve()
                .toEntity(ListLinksResponse.class)
                .getBody());
    }

    public Optional<LinkResponse> addLink(long chatId, AddLinkRequest link) {
        return Optional.of(scrapperRestClient.post()
                .uri("/links")
                .header("Tg-Chat-Id", String.valueOf(chatId))
                .body(link)
                .retrieve()
                .toEntity(LinkResponse.class)
                .getBody());
    }

    public boolean removeLink(long chatId, URI uri) {
        return scrapperRestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/links/delete")
                        .queryParam("url", uri)
                        .build())
                .header("Tg-Chat-Id", String.valueOf(chatId))
                .retrieve()
                .toBodilessEntity()
                .getStatusCode().is2xxSuccessful();
    }
}
