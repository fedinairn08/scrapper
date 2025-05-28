package bot.bot.dto.scrapper.response;

import java.net.URI;

public record LinkResponse(
        Long id,
        URI url) {
}
