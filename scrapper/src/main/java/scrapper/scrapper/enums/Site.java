package scrapper.scrapper.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Site {
    GITHUB("github.com"),
    STACK_OVERFLOW("stackoverflow.com");

    private final String host;
}
