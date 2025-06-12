package linkparser.linkparser.model;

import java.net.URI;

public record GitHubResult(URI url, String user, String repo) implements LinkParserResult {
}
