package bot.bot.handler;

import bot.bot.client.ScrapperClient;
import bot.bot.tg.Bot;
import bot.bot.tg.SendMessageAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UntrackCommandHandler extends MessageHandler {
    private final ScrapperClient scrapperClient;

    public UntrackCommandHandler(final Bot bot, final ScrapperClient scrapperClient) {
        super(bot);
        this.scrapperClient = scrapperClient;
    }

    @Override
    public void handleMessage(final Update update) {
        Message message = update.message();
        Long chatId = message.chat().id();
        String text = message.text().trim();

        List<String> parts = new ArrayList<>(List.of(text.split(" ")));
        String command = parts.removeFirst();
        int successCount = 0;

        if ("/untrack".equals(command)) {
            if (parts.isEmpty()) {
                String helpMessage = "Чтобы удалить ссылку, отправьте команду /untrack с нужными ссылками, "
                    +
                    "разделёнными пробелами.";
                bot.send(new SendMessageAdapter(chatId, helpMessage).getSendMessage());
                return;
            }

            List<URI> uris = parseUris(parts);
            StringBuilder responseBuilder = new StringBuilder();

            if (uris.isEmpty()) {
                responseBuilder.append("Ни одну из указанных ссылок не удалось распознать.");
            } else {
                for (String rawUri : parts) {
                    try {
                        URI uri = new URI(rawUri);
                        boolean result = scrapperClient.removeLink(chatId, uri);
                        if (result) {
                            successCount++;
                            responseBuilder.append("✅ ").append(uri).append("\n");
                        } else {
                            responseBuilder.append("❌ Не найдена ссылка: ").append(uri).append("\n");
                        }
                    } catch (Exception e) {
                        responseBuilder.append("⚠️ Некорректная ссылка: ").append(rawUri).append("\n");
                    }
                }
            }

            String summary = String.format(
                    "Удалено %d из %d ссылок\n",
                    successCount,
                    parts.size()
            );

            responseBuilder.insert(0, summary);

            bot.send(new SendMessageAdapter(chatId, responseBuilder.toString()).getSendMessage());
        } else {
            nextHandler.handleMessage(update);
        }
    }

    private List<URI> parseUris(final List<String> strings) {
        List<URI> result = new ArrayList<>();
        for (String s : strings) {
            try {
                result.add(new URI(s));
            } catch (URISyntaxException e) {
                log.warn("Невалидный URI: {}", s);
            }
        }
        return result;
    }
}
