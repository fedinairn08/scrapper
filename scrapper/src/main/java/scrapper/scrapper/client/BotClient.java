package scrapper.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import scrapper.scrapper.dto.request.LinkUpdateRequest;

@Component
@RequiredArgsConstructor
public class BotClient {
    private final RestClient botRestClient;

    public void updateLink(final LinkUpdateRequest linkUpdateRequest) {

        botRestClient.post()
                .uri("/updates")
                .contentType(MediaType.APPLICATION_JSON)
                .body(linkUpdateRequest)
                .retrieve()
                .toBodilessEntity();
    }
}
