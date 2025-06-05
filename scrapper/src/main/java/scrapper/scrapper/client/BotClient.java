package scrapper.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import scrapper.scrapper.dto.request.AddLinkRequest;
import scrapper.scrapper.dto.response.LinkResponse;

@Component
@RequiredArgsConstructor
public class BotClient {
    private final WebClient botWebClient;

    @Value("${baseUrl.scrapper}")
    private String scrapperUrl;

    public LinkResponse addLink(AddLinkRequest addLinkRequest) {
        return botWebClient.post()
                .uri(scrapperUrl + "/links")
                .bodyValue(addLinkRequest)
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }
}
