package bot.bot.handler;

import bot.bot.client.ScrapperClient;
import bot.bot.tg.Bot;
import bot.bot.tg.SendMessageAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartCommandHandler extends MessageHandler {
    private final ScrapperClient scrapperClient;

    public StartCommandHandler(Bot bot, ScrapperClient scrapperClient) {
        super(bot);
        this.scrapperClient = scrapperClient;
    }

    @Override
    public void handleMessage(Update update) {
        Message message = update.message();
        if (message.text().equals("/start")) {
            try {
                scrapperClient.registerChat(1);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            bot.send(new SendMessageAdapter(message.chat().id(), defaultMassage + "start")
                    .getSendMessage());
        } else {
            nextHandler.handleMessage(update);
        }
    }
}
