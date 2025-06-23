package scrapper.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import scrapper.scrapper.configuration.RabbitMQConfig;
import scrapper.scrapper.dto.request.LinkUpdateRequest;

@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer {

    private final RabbitTemplate rabbitTemplate;

    private final RabbitMQConfig rabbitMQConfig;

    public void send(final LinkUpdateRequest update) {
        rabbitTemplate.convertAndSend(
                rabbitMQConfig.exchange(),
                rabbitMQConfig.queue(),
                update
        );
    }
}
