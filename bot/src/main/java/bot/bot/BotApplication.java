package bot.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BotApplication {
    private BotApplication() {

    }

	public static void main(final String[] args) {
		SpringApplication.run(BotApplication.class, args);
	}
}
