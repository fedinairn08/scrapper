package scrapper.scrapper.dto;

import lombok.Getter;
import scrapper.scrapper.enums.Site;

import java.net.URI;

@Getter
public abstract class LinkData {
    private URI url;
    private Site site;
}
