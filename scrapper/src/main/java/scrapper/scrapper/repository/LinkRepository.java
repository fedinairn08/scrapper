package scrapper.scrapper.repository;

import scrapper.scrapper.entity.Link;

import java.sql.Timestamp;
import java.util.List;

public interface LinkRepository {
    Link save(Link link);

    void remove(Long linkId);

    List<Link> findAll();

    void updateLastUpdate(Long linkId, Timestamp timeUpdate);

    List<Link> findAllOutdatedLinks(Timestamp timestamp);

    void deleteAllByChat_ChatId(Long chatId);
}
