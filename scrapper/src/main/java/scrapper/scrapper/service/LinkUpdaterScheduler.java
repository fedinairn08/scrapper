package scrapper.scrapper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import scrapper.scrapper.entity.Link;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinkUpdaterScheduler {
    @Scheduled(fixedDelayString = "#{@scheduler.interval().toMillis()}")
    public void update() {
        log.info("update");
    }

    public void updateLinks(List<Link> links) {
        for (Link link: links) {

        }
    }
}
