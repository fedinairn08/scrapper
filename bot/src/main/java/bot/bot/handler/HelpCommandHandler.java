package bot.bot.handler;

import bot.bot.tg.Bot;
import bot.bot.tg.SendMessageAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

public class HelpCommandHandler extends MessageHandler {
    private String HELP_MESSAGE;

    public HelpCommandHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handleMessage(Update update) {
        Message message = update.message();
        if (message.text().equals("/help")) {
            bot.send(new SendMessageAdapter(message.chat().id(), getHelpMessage())
                    .getSendMessage());
        } else {
            nextHandler.handleMessage(update);
        }
    }

    private String getHelpMessage() {
        if (HELP_MESSAGE == null) {
            HELP_MESSAGE = """
                    /start -- зарегистрировать пользователя
                    /help -- вывести окно с командами
                    /track -- начать отслеживание ссылки
                    /untrack -- прекратить отслеживание ссылки
                    /list -- показать список отслеживаемых ссылок
                    """;
        }
        return HELP_MESSAGE;
    }
}
