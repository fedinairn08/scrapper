package scrapper.scrapper.dto.stackoverflow;

import java.time.OffsetDateTime;

public record StackOverflowQuestion(
        Boolean is_answered,
        OffsetDateTime last_activity_date,
        OffsetDateTime creation_date,
        OffsetDateTime last_edit_date,
        Long question_id) {
}
