package scrapper.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import scrapper.scrapper.dto.response.StackOverflowQuestionResponse;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class StackOverflowClient {
    private final WebClient stackOverflowWebClient;

    @Value("${default.timeout}")
    private Integer defaultTimeout;

    public StackOverflowQuestionResponse getQuestionInfo(Long id) {
        return stackOverflowWebClient.get()
                .uri("/questions/{id}?site=stackoverflow", id)
                .retrieve()
                .bodyToMono(StackOverflowQuestionResponse.class)
                .timeout(Duration.ofSeconds(defaultTimeout))
                .blockOptional()
                .orElse(null);
    }
}
