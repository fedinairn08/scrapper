package bot.bot.handler;

import bot.bot.tg.Bot;
import bot.bot.tg.SendMessageAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

@Component
public class ListCommandHandler extends MessageHandler {
    public ListCommandHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handleMessage(Update update) {
        Message message = update.message();
        if (message.text().equals("/list")) {
            bot.send(new SendMessageAdapter(message.chat().id(), defaultMassage + "list")
                    .getSendMessage());
        } else {
            nextHandler.handleMessage(update);
        }
    }
}
