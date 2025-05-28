package bot.bot.tg;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;

public class SendMessageAdapter {
    private final SendMessage message;

    public SendMessageAdapter(Long chatId, String text) {
        this.message = new SendMessage(chatId, text)
                .parseMode(ParseMode.Markdown);
    }

    public SendMessage getSendMessage() {
        return message;
    }
}
