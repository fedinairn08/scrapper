package linkparser.linkparser.service;

import linkparser.linkparser.model.GitHubResult;
import linkparser.linkparser.model.LinkParserResult;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.regex.Pattern;

@Component
public class LinkParserGitHub extends LinkParser {
    private static final Pattern GITHUB_PATTERN =
            Pattern.compile("^/([^/]+)/([^/]+)/?$");

    @Override
    public LinkParserResult parseUrl(URI url) {
        if ("github.com".equals(url.getHost())) {
            String path = url.getPath();
            var matcher = GITHUB_PATTERN.matcher(path);
            if (matcher.matches()) {
                return new GitHubResult(url, matcher.group(1), matcher.group(2));
            }
        }
        return parseNext(url);
    }
}
