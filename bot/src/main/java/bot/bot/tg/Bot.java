package bot.bot.tg;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Getter
@Slf4j
public class Bot {
    private final TelegramBot telegramBot;

    public <T extends BaseRequest<T, R>, R extends BaseResponse> void send(final T request) {
        telegramBot.execute(request, new Callback<T, R>() {
            @Override
            public void onResponse(final T t, final R r) {
                log.info("Успешный ответ на запрос: {}", t);
            }

            @Override
            public void onFailure(final T t, final IOException e) {
                System.out.println("onFailure");
                log.error("Ошибка при выполнении запроса {}: {}", t, e.getMessage(), e);
            }
        });
    }

    public void registerCommands() {
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("/start", "Зарегистрировать пользователя"));
        commands.add(new BotCommand("/help", "Вывести окно с командами"));
        commands.add(new BotCommand("/track", "Начать отслеживание ссылки"));
        commands.add(new BotCommand("/untrack", "Прекратить отслеживание ссылки"));
        commands.add(new BotCommand("/list", "Показать список отслеживаемых ссылок"));

        SetMyCommands setCommands = new SetMyCommands(commands.toArray(new BotCommand[0]));
        telegramBot.execute(setCommands);
    }
}
