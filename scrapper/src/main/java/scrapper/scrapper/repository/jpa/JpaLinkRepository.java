package scrapper.scrapper.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import scrapper.scrapper.entity.Link;

import java.sql.Timestamp;
import java.util.List;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    List<Link> findAllByLastUpdateLessThan(Timestamp timestamp);

    void deleteAllByChat_ChatId(Long chatId);
}
