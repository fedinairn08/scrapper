package bot.bot.handler;

import bot.bot.tg.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MessageHandlerConfiguration {

    private final Bot bot;

    @Bean
    public MessageHandler messageHandler() {
        MessageHandler messageHandler = new StartCommandHandler(bot);
        messageHandler.setNextHandler(new HelpCommandHandler(bot))
                .setNextHandler(new TrackCommandHandler(bot))
                .setNextHandler(new UntrackCommandHandler(bot))
                .setNextHandler(new ListCommandHandler(bot))
                .setNextHandler(new DefaultHandler(bot));
        return messageHandler;
    }
}
