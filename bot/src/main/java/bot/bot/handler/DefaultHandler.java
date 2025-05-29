package bot.bot.handler;


import bot.bot.tg.Bot;
import bot.bot.tg.SendMessageAdapter;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

@Component
final class DefaultHandler extends MessageHandler {
    public DefaultHandler(Bot bot) {
        super(bot);
    }

    @Override
    public void handleMessage(Update update) {
        bot.send(new SendMessageAdapter(update.message().chat().id(), "Нет подходящего обработчика")
                .getSendMessage()
        );
    }
}
