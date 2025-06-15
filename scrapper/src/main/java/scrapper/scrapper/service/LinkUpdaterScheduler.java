package scrapper.scrapper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import scrapper.scrapper.entity.Link;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinkUpdaterScheduler {

    private final LinkService linkService;

    @Value("${scheduler.update}")
    private Integer timeLinkUpdate;

    @Scheduled(fixedDelayString = "#{@scheduler.interval().toMillis()}")
    public void update() {
        List<Link> links = linkService.findAllOutdatedLinks(new Timestamp(System.currentTimeMillis() - timeLinkUpdate));
        if (links != null) {
            linkService.updateLinks(links);
        }
    }
}
