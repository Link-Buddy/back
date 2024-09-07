package com.linkbuddy.domain.link;

import com.linkbuddy.global.config.jwt.SecurityUtil;
import com.linkbuddy.global.entity.Link;
import com.linkbuddy.global.util.ResponseMessage;
import com.linkbuddy.global.util.StatusEnum;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("links")
@RestController
@Validated
public class LinkController {
  @Autowired
  private LinkService linkService;
  @Autowired
  SecurityUtil securityUtil;


  @GetMapping("category/{categoryId}")
  public ResponseEntity getLinksByCategoryId(@PathVariable("categoryId") Long categoryId) throws Exception {
    Long userId = securityUtil.getCurrentUserId();
    List<Link> links = linkService.findMyByCategoryId(categoryId, userId);
    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(links)
            .build());
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
  public ResponseEntity createLink(@RequestBody @Valid LinkDto.Create createDto) throws Exception {
    Long userId = securityUtil.getCurrentUserId();
    Link newLink = linkService.save(createDto, userId);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(new LinkDto.Create(newLink))
            .build());
  }


  @PutMapping("{id}")
  public ResponseEntity updateLink(@PathVariable("id") Long id, @RequestBody LinkDto.Update updateDto) throws Exception {
    Long userId = getCurrentUserId();
    Link updatedLink = linkService.update(id, updateDto, userId);
    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(new LinkDto.Update(updatedLink))
            .build());
  }

  @DeleteMapping("{id}")
  public ResponseEntity deleteLink(@PathVariable("id") Long id) throws Exception {
    Long userId = getCurrentUserId();
    linkService.delete(id, userId);
    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .build());
  }

  @PutMapping("change-category")
  public ResponseEntity changeCategory(@RequestParam("categoryId") Long newCategoryId, @RequestBody List<Long> linkIds) throws Exception {

    linkService.changeCategoryIdByIds(linkIds, newCategoryId);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK).data("")
            .build());
  }

  private Long getCurrentUserId() {
    return (long) 1234;
    //userServic.getCurrentUserId();
  }
}
