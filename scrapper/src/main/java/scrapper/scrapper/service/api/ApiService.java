package scrapper.scrapper.service.api;

import linkparser.linkparser.model.LinkParserResult;
import scrapper.scrapper.entity.Link;

public abstract class ApiService {

    protected ApiService nextService;

    public ApiService setNextService(ApiService nextService) {
        this.nextService = nextService;
        return nextService;
    }

    public abstract String checkUpdate(LinkParserResult linkParserResult, Link link);
}
