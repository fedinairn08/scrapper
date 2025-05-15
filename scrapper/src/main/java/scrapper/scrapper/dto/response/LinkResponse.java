package scrapper.scrapper.dto.response;

import java.net.URI;

public record LinkResponse(
        Long id,
        URI url) {
}
