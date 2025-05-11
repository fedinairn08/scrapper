package linkparser.linkparser.service;

import linkparser.linkparser.model.LinkParserResult;
import linkparser.linkparser.model.StackOverflowResult;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.regex.Pattern;

@Component
public class LinkParserStackOverflow extends LinkParser {
    private static final Pattern STACKOVERFLOW_PATTERN =
            Pattern.compile("^/questions/(\\d+)/.*$");

    @Override
    public LinkParserResult parseUrl(URI url) {
        if ("stackoverflow.com".equals(url.getHost())) {
            String path = url.getPath();
            var matcher = STACKOVERFLOW_PATTERN.matcher(path);
            if (matcher.matches()) {
                return new StackOverflowResult(Integer.parseInt(matcher.group(1)));
            }
        }
        return parseNext(url);
    }
}
