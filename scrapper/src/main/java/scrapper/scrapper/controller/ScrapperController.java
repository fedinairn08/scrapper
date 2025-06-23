package scrapper.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scrapper.scrapper.dto.request.AddLinkRequest;
import scrapper.scrapper.dto.response.LinkResponse;
import scrapper.scrapper.dto.response.ListLinksResponse;
import scrapper.scrapper.entity.Link;
import scrapper.scrapper.mapper.LinkMapper;
import scrapper.scrapper.service.ChatService;
import scrapper.scrapper.service.LinkService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScrapperController {

    private final ChatService chatService;

    private final LinkService linkService;

    private final LinkMapper linkMapper;

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<Void> registerChat(@PathVariable final Long id) {
        chatService.register(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable final Long id) {
        chatService.unregister(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader("Tg-Chat-Id") final Long tgChatId) {
        List<Link> links = linkService.listAll(tgChatId);
        return ResponseEntity.ok(linkMapper.toListLinksResponse(links));
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLink(@RequestHeader("Tg-Chat-Id") final Long tgChatId,
                                                @RequestBody final AddLinkRequest request) {
        Link link = linkService.add(tgChatId, request.link());
        return ResponseEntity.ok(linkMapper.toLinkResponse(link));
    }

    @DeleteMapping("/links/delete")
    public ResponseEntity<Void> removeLink(@RequestHeader("Tg-Chat-Id") final Long tgChatId,
                                           @RequestParam("url") final URI url) {
        linkService.remove(tgChatId, url);
        return ResponseEntity.ok().build();
    }
}
