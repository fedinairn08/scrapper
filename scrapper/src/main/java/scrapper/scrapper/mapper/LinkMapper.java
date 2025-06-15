package scrapper.scrapper.mapper;

import org.mapstruct.Mapper;
import scrapper.scrapper.dto.response.LinkResponse;
import scrapper.scrapper.dto.response.ListLinksResponse;
import scrapper.scrapper.entity.Link;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LinkMapper {
    LinkResponse toLinkResponse(Link link);

    default ListLinksResponse toListLinksResponse(List<Link> links) {
        if (links == null) return new ListLinksResponse(Collections.emptyList(), 0);

        List<LinkResponse> responses = links.stream()
                .map(this::toLinkResponse)
                .toList();

        return new ListLinksResponse(responses, responses.size());
    }
}
