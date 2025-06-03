package scrapper.scrapper.repository;

import scrapper.scrapper.entity.Link;

import java.util.List;

public interface LinkRepository {
    Link save(Link link);

    void remove(Long linkId);

    List<Link> findAll();
}
