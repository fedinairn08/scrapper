package scrapper.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scrapper.scrapper.dto.request.AddLinkRequest;
import scrapper.scrapper.dto.request.RemoveLinkRequest;
import scrapper.scrapper.dto.response.LinkResponse;
import scrapper.scrapper.dto.response.ListLinksResponse;
import scrapper.scrapper.entity.Link;
import scrapper.scrapper.service.ChatService;
import scrapper.scrapper.service.LinkService;
import scrapper.scrapper.utils.DtoMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScrapperController {

    private final ChatService chatService;

    private final LinkService linkService;

    private final DtoMapper dtoMapper;

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<Void> registerChat(@PathVariable Long id) {
        chatService.register(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        chatService.unregister(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader("Tg-Chat-Id") Long tgChatId) {
        List<Link> links = linkService.listAll(tgChatId);
        return ResponseEntity.ok(dtoMapper.convertListLinkToListLinksResponse(links));
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLink(@RequestHeader("Tg-Chat-Id") Long tgChatId,
                                                @RequestBody AddLinkRequest request) {
        Link link = linkService.add(tgChatId, request.link());
        return ResponseEntity.ok(dtoMapper.convertLinkToLinkResponse(link));
    }

    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> removeLink(@RequestHeader("Tg-Chat-Id") Long tgChatId,
                                                   @RequestBody RemoveLinkRequest request) {
        Link link = linkService.remove(tgChatId, request.link());
        return ResponseEntity.ok(dtoMapper.convertLinkToLinkResponse(link));
    }
}
