package com.linkbuddy.domain.link;

import com.linkbuddy.domain.buddyUser.BuddyUserService;
import com.linkbuddy.domain.category.CategoryService;
import com.linkbuddy.global.config.jwt.SecurityUtil;
import com.linkbuddy.global.entity.Category;
import com.linkbuddy.global.entity.Link;
import com.linkbuddy.global.util.ResponseMessage;
import com.linkbuddy.global.util.StatusEnum;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@RequestMapping("links")
@RestController
@Validated
@Slf4j
public class LinkController {
  @Autowired
  private LinkService linkService;
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private BuddyUserService buddyUserService;

  @Autowired
  SecurityUtil securityUtil;


  @GetMapping("weekly")
  public ResponseEntity getWeeklyLinkStatus() throws Exception {
    Long userId = securityUtil.getCurrentUserId();
    System.out.println(userId);
    // 이번 주 월요일과 일요일 계산
    LocalDate today = LocalDate.now();
    LocalDate monday = today.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
    LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));

    // 요일별 등록 여부 확인
    Map<String, Boolean> weeklyStatus = linkService.getWeeklyLinkStatus(userId, monday, sunday);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(weeklyStatus)
            .build());
  }

  @GetMapping("category/{categoryId}")
  public ResponseEntity getLinksByCategoryId(@PathVariable("categoryId") Long categoryId,
                                             @RequestParam(value = "buddyId", required = false) Long buddyId) throws Exception {
    // userId 조회
    Long userId = securityUtil.getCurrentUserId();
    // 카테고리 데이터 조회
    Category category = categoryService.getCategory(categoryId);
    List<LinkDto.LinkInfoData> links;

    if (buddyId != null) {
      // buddy
      links = linkService.findBuddyLinks(categoryId, userId, buddyId);
    } else {
      // 내 리스트 조회
      links = linkService.findMyByCategoryId(categoryId, userId);
    }

    Map<String, Object> linkMap = new HashMap<>();
    linkMap.put("category", category);
    linkMap.put("links", links);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(linkMap)
            .build());
  }

  @PostMapping("recent-view")
  public ResponseEntity<?> getLinksByCategoryId(@RequestBody Map<String, List<Long>> body) throws Exception {
    List<Long> linkIds = body.get("linkIds");
    List<LinkDto.SearchResponse> links = linkService.findRecentViewLinks(linkIds);

    Map<String, Object> linkMap = new HashMap<>();
    linkMap.put("links", links);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(linkMap)
            .build());
  }

  //좋아요 누른 게시글
  @GetMapping("favorite")
  public ResponseEntity getMyFavoriteLinks() throws Exception {
    Long userId = securityUtil.getCurrentUserId();
    List<LinkDto.SearchLinkData> links = linkService.getMyFavoriteLinks(userId);


    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(links)
            .build());
  }

  //내가 등록한 링크
  @GetMapping("registed")
  public ResponseEntity getMyRegistedLinks() throws Exception {
    Long userId = securityUtil.getCurrentUserId();
    List<LinkDto.SearchResponse> links = linkService.getMyRegistedLinks(userId);

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
