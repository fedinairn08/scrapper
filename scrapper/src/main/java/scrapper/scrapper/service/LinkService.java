package scrapper.scrapper.service;

import scrapper.scrapper.entity.Link;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;

public interface LinkService {
    Link add(long tgChatId, URI url);

    void remove(long tgChatId, URI url);

    List<Link> listAll(long tgChatId);

    void updateLastUpdate(Long linkId, Timestamp timeUpdate);

    List<Link> findAllOutdatedLinks(Timestamp timestamp);

    void deleteAllByChat_ChatId(Long chatId);
}
