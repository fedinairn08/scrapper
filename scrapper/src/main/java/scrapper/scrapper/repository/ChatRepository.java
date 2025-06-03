package scrapper.scrapper.repository;

import scrapper.scrapper.entity.Chat;

import java.util.List;

public interface ChatRepository {
    Chat save(Chat chat);

    void remove(Long chatId);

    List<Chat> findAll();
}
