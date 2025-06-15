package bot.bot.controller;

import bot.bot.dto.LinkUpdate;
import bot.bot.tg.Bot;
import bot.bot.tg.SendMessageAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BotController {

    private final Bot bot;

    @PostMapping("/updates")
    public ResponseEntity<Void> sendUpdate(@RequestBody LinkUpdate linkUpdate) {
        bot.send(new SendMessageAdapter(linkUpdate.tgChatIds().getFirst(), linkUpdate.description()).getSendMessage());
        return ResponseEntity.ok().build();
    }
}
