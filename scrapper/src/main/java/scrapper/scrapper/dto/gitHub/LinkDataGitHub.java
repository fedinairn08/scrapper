package scrapper.scrapper.dto.gitHub;

import lombok.Data;
import scrapper.scrapper.dto.LinkData;

@Data
public final class LinkDataGitHub extends LinkData {
    private String user;
    private String repository;
}
