package scrapper.scrapper.repository;

import org.springframework.stereotype.Repository;
import scrapper.scrapper.entity.Chat;

import java.util.List;

@Repository
public interface ChatRepository {
    Chat save(Chat chat);

    void remove(Long chatId);

    List<Chat> findAll();
}
