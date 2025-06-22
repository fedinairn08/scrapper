package bot.bot.handler;

import bot.bot.tg.Bot;
import com.pengrad.telegrambot.model.Update;

public abstract class MessageHandler {
    protected MessageHandler nextHandler;

    protected Bot bot;

    public MessageHandler(Bot bot) {
        this.bot = bot;
    }

    public MessageHandler setNextHandler(MessageHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    public abstract void handleMessage(Update update);
}
