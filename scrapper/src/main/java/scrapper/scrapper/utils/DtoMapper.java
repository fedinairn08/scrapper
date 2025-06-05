package scrapper.scrapper.utils;

import org.springframework.stereotype.Component;
import scrapper.scrapper.dto.response.LinkResponse;
import scrapper.scrapper.dto.response.ListLinksResponse;
import scrapper.scrapper.entity.Link;

import java.util.List;

@Component
public class DtoMapper {
    public LinkResponse convertLinkToLinkResponse(Link link) {
        return new LinkResponse(link.getId(), link.getUrl());
    }

    public ListLinksResponse convertListLinkToListLinksResponse(List<Link> links) {
        return new ListLinksResponse(links.stream()
                .map(this::convertLinkToLinkResponse)
                .toList(),
                links.size());
    }
}
