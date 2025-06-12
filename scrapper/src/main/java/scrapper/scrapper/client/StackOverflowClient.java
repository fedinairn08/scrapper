package scrapper.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import scrapper.scrapper.dto.response.StackOverflowQuestionResponse;

@Component
@RequiredArgsConstructor
public class StackOverflowClient {

    private final RestClient stackOverflowRestClient;

    public StackOverflowQuestionResponse getQuestionInfo(Long id) {
        return stackOverflowRestClient.get()
                .uri("/questions/{id}?site=stackoverflow", id)
                .retrieve()
                .body(StackOverflowQuestionResponse.class);
    }
}
