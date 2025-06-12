package linkparser.linkparser.model;

import java.net.URI;

public record StackOverflowResult(URI url, Long questionId) implements LinkParserResult {
}
