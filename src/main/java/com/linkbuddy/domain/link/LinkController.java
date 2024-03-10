package com.linkbuddy.domain.link;

import com.linkbuddy.global.entity.Buddy;
import com.linkbuddy.global.entity.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
  public ResponseEntity<Link> getLink(@PathVariable("id") Long id) {

    Link link = linkService.findOneActive(id);
    return ResponseEntity.ok(link);
  }

  @PostMapping
  public ResponseEntity<Link> createLink(@RequestBody Link link) {
    System.out.println("Received link: " + link.toString());
    Link newLink = linkService.save(link);
    return ResponseEntity.ok(newLink);
  }

  @PatchMapping("{id}")
  public ResponseEntity<Link> updateLink(@PathVariable("id") Long id, @RequestBody Link link) {
    Link updatedLink = linkService.update(id, link);
    return ResponseEntity.ok(updatedLink);
  }

  @DeleteMapping("{id}")
  public void deleteLink(@PathVariable("id") Long id) {
    Long userId = getCurrentUserId();
    linkService.delete(id, userId);
  }

  private Long getCurrentUserId() {
    return (long) 1234;
    //userServic.getCurrentUserId();
  }
}
