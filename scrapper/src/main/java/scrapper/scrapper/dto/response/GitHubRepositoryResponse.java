package scrapper.scrapper.dto.response;

import java.time.OffsetDateTime;

public record GitHubRepositoryResponse(
        Long id,
        String node_id,
        String name,
        String full_name,
        String html_url,
        String description,
        OffsetDateTime pushed_at,
        OffsetDateTime created_at,
        OffsetDateTime updated_at) {

}
