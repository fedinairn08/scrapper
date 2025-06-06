package scrapper.scrapper.service.api;

import org.springframework.context.annotation.ComponentScan;
import scrapper.scrapper.dto.LinkData;

@ComponentScan("linkparser.linkparser.service")
public abstract class ApiService {

    protected ApiService nextService;

    public ApiService setNextService(ApiService nextService) {
        this.nextService = nextService;
        return nextService;
    }

    public abstract String checkUpdate(LinkData linkData);
}
