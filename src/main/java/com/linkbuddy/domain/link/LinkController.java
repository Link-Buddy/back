package com.linkbuddy.domain.link;

import com.linkbuddy.global.config.jwt.SecurityUtil;
import com.linkbuddy.global.entity.Link;
import com.linkbuddy.global.util.ResponseMessage;
import com.linkbuddy.global.util.StatusEnum;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("links")
@RestController
@Validated
@Slf4j
public class LinkController {
  @Autowired
  private LinkService linkService;
  @Autowired
  SecurityUtil securityUtil;


  @GetMapping("category/{categoryId}")
  public ResponseEntity getLinksByCategoryId(@PathVariable("categoryId") Long categoryId) throws Exception {
    Long userId = securityUtil.getCurrentUserId();
    List<LinkDto.LinkInfo> links = linkService.findMyByCategoryId(categoryId, userId);
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

  //좋아요 누른 게시글
  @GetMapping("favorite")
  public ResponseEntity getMyFavoriteLinks() throws Exception {
    Long userId = securityUtil.getCurrentUserId();
    List<Link> links = linkService.getMyFavoriteLinks(userId);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(links)
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
    Long userId = securityUtil.getCurrentUserId();
    Link updatedLink = linkService.update(id, updateDto, userId);
    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(new LinkDto.Update(updatedLink))
            .build());
  }

  @DeleteMapping("{id}")
  public ResponseEntity deleteLink(@PathVariable("id") Long id) throws Exception {
    Long userId = securityUtil.getCurrentUserId();
    linkService.delete(id, userId);
    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(true)
            .build());
  }

  @PutMapping("change-category/{categoryId}")
  public ResponseEntity changeCategory(@PathVariable("categoryId") Long newCategoryId, @RequestBody List<Long> linkIds) throws Exception {
    Long userId = securityUtil.getCurrentUserId();
    linkService.changeCategoryIdByIds(linkIds, newCategoryId, userId);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK).data("")
            .build());
  }

  private Long getCurrentUserId() {
    return (long) 1234;
    //userServic.getCurrentUserId();
  }

  @GetMapping("/search")
  public ResponseEntity searchLinks(@RequestParam("keyword") String keyword) throws Exception {
    Long userId = securityUtil.getCurrentUserId();
    List<LinkDto.SearchResponse> searchList = linkService.searchLinks(userId, keyword);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK).data(searchList)
            .build());
  }
}
