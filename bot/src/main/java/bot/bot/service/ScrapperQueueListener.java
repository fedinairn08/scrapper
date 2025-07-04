package bot.bot.service;

import bot.bot.tg.Bot;
import bot.bot.tg.SendMessageAdapter;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import scrapper.scrapper.dto.request.LinkUpdateRequest;

@Component
@RequiredArgsConstructor
@RabbitListener(queues = "${rabbit.queue}")
public class ScrapperQueueListener {

    private final Bot bot;

    private final Counter rabbitCounter;

    @RabbitHandler
    public void receiver(final LinkUpdateRequest update) {
        bot.send(new SendMessageAdapter(update.tgChatIds().getFirst(), update.description()).getSendMessage());
        rabbitCounter.increment();
    }
}
