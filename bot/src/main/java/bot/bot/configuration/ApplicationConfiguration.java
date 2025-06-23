package bot.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final ApplicationConfig config;

    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(config.token());
    }

    @Bean
    public Counter rabbitCounter(MeterRegistry prometheusMeterRegistry) {
        return Counter.builder("rabbitmq.messages.processed")
            .description("Number of processed RabbitMQ messages")
            .register(prometheusMeterRegistry);
    }
}
