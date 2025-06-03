package scrapper.scrapper.repository.jdbcImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import scrapper.scrapper.entity.Chat;
import scrapper.scrapper.repository.ChatRepository;

import java.sql.PreparedStatement;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ChatRepositoryJdbcImpl implements ChatRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Chat save(Chat chat) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO chats (chat_id) VALUES (?)",
                    new String[] {"id"});
            ps.setLong(1, chat.getChatId());
            return ps;
        }, keyHolder);

        chat.setId((long) keyHolder.getKey());
        return chat;
    }

    @Override
    public void remove(Long chatId) {
        jdbcTemplate.update("DELETE FROM chat WHERE id = ?", chatId);
    }

    @Override
    public List<Chat> findAll() {
        return null;
    }
}
