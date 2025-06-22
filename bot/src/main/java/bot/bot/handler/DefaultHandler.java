package bot.bot.handler;


import bot.bot.tg.Bot;
import bot.bot.tg.SendMessageAdapter;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

@Component
final class DefaultHandler extends MessageHandler {
    DefaultHandler(final Bot bot) {
        super(bot);
    }

    @Override
    public void handleMessage(final Update update) {
        bot.send(new SendMessageAdapter(update.message().chat().id(), "Нет подходящего обработчика")
                .getSendMessage()
        );
    }
}
