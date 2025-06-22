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

    public StartCommandHandler(final Bot bot, final ScrapperClient scrapperClient) {
        super(bot);
        this.scrapperClient = scrapperClient;
    }

    @Override
    public void handleMessage(final Update update) {
        Message message = update.message();

        String text = message.text().trim();
        String[] parts = text.split(" ");

        String command = parts[0];

        if ("/start".equals(command)) {
            if (parts.length == 1) {
                bot.send(new SendMessageAdapter(message.chat().id(),
                        "Чтобы зарегистрировать пользователя, отправьте команду /start с id чата пользователя, "
                            +
                            "разделёнными пробелами."
                ).getSendMessage());
            } else {
                try {
                    long chatId = Long.parseLong(parts[1]);
                    scrapperClient.registerChat(chatId);
                    bot.send(new SendMessageAdapter(message.chat().id(), "Зарегистрирован чат с ID: " + chatId)
                            .getSendMessage());
                } catch (NumberFormatException e) {
                    log.error("Некорректный ID чата: {}", parts[1], e);
                    bot.send(new SendMessageAdapter(message.chat().id(),
                            "ID чата должен быть числом: " + parts[1])
                            .getSendMessage());
                } catch (Exception e) {
                    log.error("Ошибка регистрации чата: {}", e.getMessage(), e);
                    bot.send(new SendMessageAdapter(message.chat().id(),
                            "Не удалось зарегистрировать чат: " + parts[1])
                            .getSendMessage());
                }
            }
        } else {
            nextHandler.handleMessage(update);
        }
    }
}
