package bot.bot.handler;

import bot.bot.client.ScrapperClient;
import bot.bot.dto.scrapper.request.AddLinkRequest;
import bot.bot.dto.scrapper.response.LinkResponse;
import bot.bot.tg.Bot;
import bot.bot.tg.SendMessageAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import linkparser.linkparser.service.LinkParseService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@ComponentScan("linkparser.linkparser.service")
@Slf4j
public class TrackCommandHandler extends MessageHandler {
    private final ScrapperClient scrapperClient;

    private final LinkParseService linkParseService;

    public TrackCommandHandler(Bot bot, ScrapperClient scrapperClient, LinkParseService linkParseService) {
        super(bot);
        this.scrapperClient = scrapperClient;
        this.linkParseService = linkParseService;
    }

    @Override
    public void handleMessage(Update update) {
        Message message = update.message();
        Long chatId = message.chat().id();
        List<String> stringUri = new ArrayList<>(List.of(message.text().split(" ")));
        String commandMessage = stringUri.remove(0);

        if ("/track".equals(commandMessage)) {
            if (stringUri.isEmpty()) {
                String messageForGetLink = "Чтобы добавить ссылку отправьте команду "
                        + "/track с нужными ссылками, разделенными пробелами.";
                bot.send(new SendMessageAdapter(chatId, messageForGetLink)
                        .getSendMessage());
            } else {
                List<URI> urls = parseUris(stringUri);
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("Добавлено %d из %d ссылок", urls.size(), stringUri.size()))
                        .append("\n");
                if (!urls.isEmpty()) {
                    sb.append("Добавлены следующие ссылки:\n");
                    for (URI uri : urls) {
                        Optional<LinkResponse> response = scrapperClient.addLink(chatId, new AddLinkRequest(uri));
                        response.ifPresent(linkResponse -> sb.append(linkResponse.url().toString()));
                    }
                }
                bot.send(new SendMessageAdapter(chatId, sb.toString())
                        .getSendMessage());
            }
        } else {
            nextHandler.handleMessage(update);
        }
    }

    private List<URI> parseUris(List<String> stringUris) {
        List<URI> uris = new ArrayList<>();
        for (String s : stringUris) {
            try {
                URI uri = new URI(s);
                if (linkParseService.parseLink(uri) != null) {
                    uris.add(uri);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return uris;
    }
}
