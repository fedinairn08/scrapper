package bot.bot.client;

import bot.bot.dto.scrapper.request.AddLinkRequest;
import bot.bot.dto.scrapper.request.RemoveLinkRequest;
import bot.bot.dto.scrapper.response.LinkResponse;
import bot.bot.dto.scrapper.response.ListLinksResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScrapperClient {
    private final WebClient scrapperWebClient;

    @Value("${default.timeout}")
    private Integer defaultTimeout;

    @Value("${baseUrl.scrapper}")
    private String scrapperUrl;

    public void registerChat(long chatId) {
        scrapperWebClient.post()
                .uri(scrapperUrl + "/tg-chat/" + chatId)
                .retrieve()
                .toBodilessEntity()
                .timeout(Duration.ofSeconds(defaultTimeout))
                .onErrorResume(throwable -> {
                    log.error(throwable.getMessage());
                    return Mono.empty();
                })
                .block();
    }

    public void deleteChat(long chatId) {
        scrapperWebClient.delete()
                .uri(scrapperUrl + "/tg-chat/" + chatId)
                .retrieve()
                .toBodilessEntity()
                .timeout(Duration.ofSeconds(defaultTimeout))
                .onErrorResume(throwable -> {
                    log.error(throwable.getMessage());
                    return Mono.empty();
                })
                .block();
    }

    public Optional<ListLinksResponse> getLinks(long chatId) {
        return scrapperWebClient.get()
                .uri(scrapperUrl + "/links")
                .header("Tg-Chat-Id", String.valueOf(chatId))
                .retrieve()
                .bodyToMono(ListLinksResponse.class)
                .timeout(Duration.ofSeconds(defaultTimeout))
                .onErrorResume(throwable -> {
                    log.error(throwable.getMessage());
                    return Mono.empty();
                })
                .blockOptional();
    }

    public Optional<LinkResponse> addLink(long chatId, AddLinkRequest link) {
        return scrapperWebClient.post()
                .uri("/links")
                .header("Tg-Chat-Id", String.valueOf(chatId))
                .bodyValue(link)
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .timeout(Duration.ofSeconds(defaultTimeout))
                .onErrorResume(throwable -> {
                    log.error(throwable.getMessage());
                    return Mono.empty();
                })
                .blockOptional();
    }

    public Optional<LinkResponse> removeLink(long chatId, RemoveLinkRequest link) {
        return scrapperWebClient.post()
                .uri(scrapperUrl + "/links/delete")
                .header("Tg-Chat-Id", String.valueOf(chatId))
                .bodyValue(link)
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .timeout(Duration.ofSeconds(defaultTimeout))
                .onErrorResume(throwable -> {
                    log.error(throwable.getMessage());
                    return Mono.empty();
                })
                .blockOptional();
    }
}
