package scrapper.scrapper.service;

import scrapper.scrapper.entity.Chat;

import java.util.Optional;

public interface ChatService {
    void register(long chatId);

    void unregister(long chatId);

    Optional<Chat> getByChatId(long tgChatId);
}
