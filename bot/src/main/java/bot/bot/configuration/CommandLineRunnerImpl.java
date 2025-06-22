package bot.bot.configuration;

import bot.bot.handler.MessageHandler;
import bot.bot.tg.Bot;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import com.pengrad.telegrambot.UpdatesListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final Bot bot;

    private final MessageHandler messageHandler;

    @Override
    public void run(final String... args) {
        bot.registerCommands();
        bot.getTelegramBot().setUpdatesListener(updates -> {
            for (Update update : updates) {
                messageHandler.handleMessage(update);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
