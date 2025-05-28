package bot.bot.tg;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Getter
@Slf4j
public class Bot {
    private final TelegramBot telegramBot;

    public <T extends BaseRequest<T, R>, R extends BaseResponse> void send(T request) {
        telegramBot.execute(request, new Callback<T, R>() {
            @Override
            public void onResponse(T t, R r) {
                log.info("Успешный ответ на запрос: {}", t);
            }

            @Override
            public void onFailure(T t, IOException e) {
                System.out.println("onFailure");
                log.error("Ошибка при выполнении запроса {}: {}", t, e.getMessage(), e);
            }
        });
    }
}
