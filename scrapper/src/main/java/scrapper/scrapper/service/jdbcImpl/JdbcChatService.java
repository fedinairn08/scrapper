package scrapper.scrapper.service.jdbcImpl;

import lombok.RequiredArgsConstructor;
import scrapper.scrapper.entity.Chat;
import scrapper.scrapper.repository.ChatRepository;
import scrapper.scrapper.service.ChatService;

import java.util.Optional;

@RequiredArgsConstructor
public class JdbcChatService implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public void register(long chatId) {
        chatRepository.save(new Chat().setChatId(chatId));
    }

    @Override
    public void unregister(long chatId) {
        chatRepository.remove(chatRepository.findByChatId(chatId).get().getId());
    }

    @Override
    public Optional<Chat> getByChatId(long tgChatId) {
        return chatRepository.findByChatId(tgChatId);
    }
}
