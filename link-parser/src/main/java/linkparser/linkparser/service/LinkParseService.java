package linkparser.linkparser.service;

import linkparser.linkparser.model.LinkParserResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class LinkParseService {
    private final LinkParser linkParser;

    public LinkParserResult parseLink(URI url) {
        return linkParser.parseUrl(url);
    }
}
