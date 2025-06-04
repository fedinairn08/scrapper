package scrapper.scrapper.repository;

import org.springframework.stereotype.Repository;
import scrapper.scrapper.entity.Link;

import java.util.List;

@Repository
public interface LinkRepository {
    Link save(Link link);

    void remove(Long linkId);

    List<Link> findAll();
}
