package com.linkbuddy.domain.link;

import com.linkbuddy.global.entity.Link;
import com.linkbuddy.global.util.ResponseMessage;
import com.linkbuddy.global.util.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("links")
@RestController
public class LinkController {
  @Autowired
  private LinkService linkService;

  @GetMapping
  public ResponseEntity<List<Link>> getLinks() {
    List<Link> links = linkService.findAll();
    return ResponseEntity.ok(links);
  }

  @GetMapping("{id}")
  public ResponseEntity getLink(@PathVariable("id") Long id) throws Exception {

    Link link = linkService.findOneActive(id);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(link)
            .build());
  }

  @PostMapping
  public ResponseEntity<Link> createLink(@RequestBody LinkDto.Create createDto) throws Exception {

    Link newLink = linkService.save(createDto);
    return ResponseEntity.ok(newLink);
  }

  @PutMapping("{id}")
  public ResponseEntity<Link> updateLink(@PathVariable("id") Long id, @RequestBody LinkDto.Update updateDto) throws Exception {
    Long userId = getCurrentUserId();
    Link updatedLink = linkService.update(id, updateDto, userId);
    return ResponseEntity.ok(updatedLink);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteLink(@PathVariable("id") Long id) throws Exception {
    Long userId = getCurrentUserId();
    linkService.delete(id, userId);
    return ResponseEntity.ok().build();
  }

  private Long getCurrentUserId() {
    return (long) 1234;
    //userServic.getCurrentUserId();
  }
}
