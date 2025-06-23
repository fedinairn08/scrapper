package scrapper.scrapper.service.jpaImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import scrapper.scrapper.entity.Chat;
import scrapper.scrapper.entity.Link;
import scrapper.scrapper.repository.jpa.JpaChatRepository;
import scrapper.scrapper.service.ChatService;
import scrapper.scrapper.service.LinkService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JpaChatService implements ChatService {

    private final JpaChatRepository jpaChatRepository;

    private final LinkService linkService;

    @Override
    public void register(final long chatId) {
        jpaChatRepository.save(new Chat().setChatId(chatId));
    }

    @Override
    @Transactional
    public void unregister(final long chatId) {
        Chat chat = jpaChatRepository.findByChatId(chatId);

        List<Link> linksToRemove = new ArrayList<>(chat.getLinks());

        for (Link link : linksToRemove) {
            linkService.remove(chatId, link.getUrl());
        }

        jpaChatRepository.delete(chat);
    }

    @Override
    public Optional<Chat> getByChatId(final long tgChatId) {
        return Optional.ofNullable(jpaChatRepository.findByChatId(tgChatId));
    }
}
