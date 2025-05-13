package linkparser.linkparser.service;

import linkparser.linkparser.model.LinkParserResult;
import lombok.Setter;

import java.net.URI;


@Setter
public abstract class LinkParser {
    protected LinkParser nextParser;

    public abstract LinkParserResult parseUrl(URI url);

    protected LinkParserResult parseNext(URI url) {
        if (nextParser != null) {
            return nextParser.parseUrl(url);
        }
        return null;
    }
}
