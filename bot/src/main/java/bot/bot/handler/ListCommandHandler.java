package bot.bot.handler;

import bot.bot.client.ScrapperClient;
import bot.bot.dto.scrapper.response.ListLinksResponse;
import bot.bot.tg.Bot;
import bot.bot.tg.SendMessageAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class ListCommandHandler extends MessageHandler {
    private final ScrapperClient scrapperClient;

    public ListCommandHandler(Bot bot, ScrapperClient scrapperClient) {
        super(bot);
        this.scrapperClient = scrapperClient;
    }

    @Override
    public void handleMessage(Update update) {
        Message message = update.message();
        if (message.text().equals("/list")) {
            try{
                Optional<ListLinksResponse> response = scrapperClient.getLinks(1);
                response.ifPresent(linkResponse -> log.info(linkResponse.toString()));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            bot.send(new SendMessageAdapter(message.chat().id(), defaultMassage + "list")
                    .getSendMessage());
        } else {
            nextHandler.handleMessage(update);
        }
    }
}
