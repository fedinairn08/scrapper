package bot.bot.handler;

import bot.bot.tg.Bot;
import bot.bot.tg.SendMessageAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

@Component
public class HelpCommandHandler extends MessageHandler {
    private String helpMessage;

    public HelpCommandHandler(final Bot bot) {
        super(bot);
    }

    @Override
    public void handleMessage(final Update update) {
        Message message = update.message();
        if (message.text().equals("/help")) {
            bot.send(new SendMessageAdapter(message.chat().id(), getHelpMessage())
                    .getSendMessage());
        } else {
            nextHandler.handleMessage(update);
        }
    }

    private String getHelpMessage() {
        if (helpMessage == null) {
            helpMessage = """
                    /start -- зарегистрировать пользователя
                    /help -- вывести окно с командами
                    /track -- начать отслеживание ссылки
                    /untrack -- прекратить отслеживание ссылки
                    /list -- показать список отслеживаемых ссылок
                    """;
        }
        return helpMessage;
    }
}
