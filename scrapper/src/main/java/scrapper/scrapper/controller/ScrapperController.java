package scrapper.scrapper.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scrapper.scrapper.configuration.dto.request.AddLinkRequest;
import scrapper.scrapper.configuration.dto.request.RemoveLinkRequest;
import scrapper.scrapper.configuration.dto.response.LinkResponse;
import scrapper.scrapper.configuration.dto.response.ListLinksResponse;

import java.util.List;

@RestController
public class ScrapperController {
    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<Void> registerChat(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader("Tg-Chat-Id") Long tgChatId) {
        return ResponseEntity.ok(new ListLinksResponse(List.of(), 0));
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLink(@RequestHeader("Tg-Chat-Id") Long tgChatId,
                                                @RequestBody AddLinkRequest request) {
        return ResponseEntity.ok(new LinkResponse(1L, request.link()));
    }

    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> removeLink(@RequestHeader("Tg-Chat-Id") Long tgChatId,
                                                   @RequestBody RemoveLinkRequest request) {
        return ResponseEntity.ok(new LinkResponse(1L, request.link()));
    }
}
