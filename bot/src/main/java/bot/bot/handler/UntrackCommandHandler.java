package bot.bot.handler;

import bot.bot.client.ScrapperClient;
import bot.bot.dto.scrapper.request.RemoveLinkRequest;
import bot.bot.dto.scrapper.response.LinkResponse;
import bot.bot.tg.Bot;
import bot.bot.tg.SendMessageAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Optional;

@Component
@Slf4j
public class UntrackCommandHandler extends MessageHandler {
    private final ScrapperClient scrapperClient;

    public UntrackCommandHandler(Bot bot, ScrapperClient scrapperClient) {
        super(bot);
        this.scrapperClient = scrapperClient;
    }

    @Override
    public void handleMessage(Update update) {
        Message message = update.message();
        if (message.text().equals("/untrack")) {
            try {
                Optional<LinkResponse> response = scrapperClient.removeLink(1,
                        new RemoveLinkRequest(new URI("http://localhost")));
                response.ifPresent(linkResponse -> log.info(linkResponse.toString()));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            bot.send(new SendMessageAdapter(message.chat().id(), defaultMassage + "untrack")
                    .getSendMessage());
        } else {
            nextHandler.handleMessage(update);
        }
    }
}
