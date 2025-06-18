package scrapper.scrapper.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import scrapper.scrapper.entity.Chat;

public interface JpaChatRepository extends JpaRepository<Chat, Long> {
    Chat findByChatId(Long tgChatId);
}
