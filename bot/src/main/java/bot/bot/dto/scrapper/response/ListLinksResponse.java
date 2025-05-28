package bot.bot.dto.scrapper.response;

import java.util.List;

public record ListLinksResponse(
        List<LinkResponse> links,
        Integer size) {
}
