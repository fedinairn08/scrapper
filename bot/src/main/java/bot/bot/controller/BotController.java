package bot.bot.controller;

import bot.bot.dto.LinkUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/updates")
public class BotController {
    @PostMapping
    public ResponseEntity<Void> sendUpdate(@RequestBody LinkUpdate request) {
        return ResponseEntity.ok().build();
    }

}
