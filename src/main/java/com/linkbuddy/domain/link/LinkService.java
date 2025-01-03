package com.linkbuddy.domain.link;

import com.linkbuddy.domain.buddyUser.repository.BuddyUserRepository;
import com.linkbuddy.domain.category.repository.CategoryRepository;
import com.linkbuddy.domain.favorite.repository.FavoriteRepository;
import com.linkbuddy.domain.link.repository.LinkRepository;
import com.linkbuddy.domain.user.dto.UserDTO;
import com.linkbuddy.global.entity.Category;
import com.linkbuddy.global.entity.Link;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class LinkService {
  @Autowired
  private LinkRepository linkRepository;
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private BuddyUserRepository buddyUserRepository;
  @Autowired
  private FavoriteRepository favoriteRepository;

  public List<Link> findAll() {
    return linkRepository.findAllActive();
  }


  public Map<String, Boolean> getWeeklyLinkStatus(Long userId, LocalDate monday, LocalDate sunday) {
    // 월요일부터 일요일까지 날짜 생성
    List<LocalDate> weekDates = IntStream.rangeClosed(0, 6)
            .mapToObj(monday::plusDays)
            .collect(Collectors.toList());

    // 각 날짜에 대해 링크 존재 여부 확인
    return weekDates.stream().collect(Collectors.toMap(
            date -> date.getDayOfWeek().name(), // 요일 이름
            date -> linkRepository.existsByUserIdAndDateBetween(userId, date.atStartOfDay(), date.atTime(23, 59, 59))
    ));
  }

  private List<LinkDto.LinkInfoData> addPreviewToLinks(List<LinkDto.LinkInfo> linkList) throws Exception {
    List<LinkDto.LinkInfoData> newLinkList = new ArrayList<>();
    for (LinkDto.LinkInfo linkInfo : linkList) {
      Map<String, String> previewData = getLinkPreviewData(linkInfo.getLinkUrl());
      LinkDto.LinkInfoData newLinkInfo = LinkDto.LinkInfoData.builder()
              .lInfo(linkInfo)
              .imageUrl(previewData.get("imageUrl"))
              .urlTitle(previewData.get("title"))
              .build();
      newLinkList.add(newLinkInfo);
    }
    return newLinkList;
  }

  public List<LinkDto.LinkInfoData> findMyByCategoryId(Long categoryId, Long userId) throws Exception {
    try {
      // 내 링크 조회 by categoryId
      List<LinkDto.LinkInfo> linkList = linkRepository.findMyLinksByCategoryId(categoryId, userId);

      // 링크 리스트 변환
      return addPreviewToLinks(linkList);

    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  public List<LinkDto.LinkInfoData> findBuddyLinks(Long categoryId, Long userId, Long buddyId) throws Exception {
    try {
      Boolean isExistBuddyUser = buddyUserRepository.existsByBuddyIdAndUserId(buddyId, userId);
      log.info("buddyUserRepository = {}", isExistBuddyUser);
      if (!isExistBuddyUser) {
        log.info("buddyUserRepository = {}", isExistBuddyUser);
        throw new IllegalArgumentException("Not exist Buddy User Data");
      }

      // 버디 링크 조회 by categoryId
      List<LinkDto.LinkInfo> linkList = linkRepository.findBuddyLinksByCategoryId(categoryId, userId);

      // 링크 리스트 변환
      return addPreviewToLinks(linkList);

    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  public List<LinkDto.SearchResponse> findRecentViewLinks(List<Long> linkIds) throws Exception {

    return linkRepository.findRecentViewLinks(linkIds);

  }

  public List<LinkDto.SearchLinkData> getMyFavoriteLinks(Long userId) throws Exception {

    List<LinkDto.SearchResponse> linkList = linkRepository.getMyFavoriteLinks(userId);

    // link preview image url 추가
    List<LinkDto.SearchLinkData> newLinkList = new ArrayList<>();
    for (LinkDto.SearchResponse linkInfo : linkList) {

      Map<String, String> previewData = getLinkPreviewData(linkInfo.getLinkUrl());
      String imageUrl = previewData.get("imageUrl") != null ? previewData.get("imageUrl") : null;
      String title = previewData.get("title") != null ? previewData.get("title") : "";

      LinkDto.SearchLinkData newLinkInfo = LinkDto.SearchLinkData.builder()
              .lInfo(linkInfo)
              .imageUrl(imageUrl)
              .urlTitle(title)
              .build();
      newLinkList.add(newLinkInfo);
    }
    return newLinkList;
  }

  public List<LinkDto.SearchResponse> getMyRegistedLinks(Long userId) throws Exception {

    return linkRepository.getMyRegistedLinks(userId);

  }


  private Link findById(Long id) {
    return linkRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Link not found." + id));
  }

  public Link findOneActive(Long id) throws Exception {
    try {
      return linkRepository.findOneActive(id);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  public LinkDto.Mylink findMyLinkCount(Long userId) {
    Long registedCount = linkRepository.findMyRegistedCount(userId);
    Long favoriteCount = linkRepository.findMyFavoriteCount(userId);

    return LinkDto.Mylink.builder().registedCount(registedCount).favoriteCount(favoriteCount).build();
  }

  public Link save(LinkDto.Create linkDto, Long userId) throws Exception {
    try {


      Link newLink = Link.builder()
              .name(linkDto.getName())
              .description(linkDto.getDescription())
              .linkUrl(linkDto.getLinkUrl())
              .categoryId(linkDto.getCategoryId())
              .userId(userId)
              .build();

      return linkRepository.save(newLink);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }

  }

  public Link update(Long id, LinkDto.Update linkDto, Long userId) throws Exception {
    try {

      Link existLink = findOneActive(id);

      if (!existLink.isHost(userId)) {
        throw new IllegalArgumentException("삭제 권한 없음"); //공통 예외?
      }
      log.info("linkDto = {}", linkDto);
      existLink.updateLink(linkDto, userId);

      log.info("existLink = {}", existLink);
      return linkRepository.save(existLink);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  public void delete(Long id, Long userId) throws Exception {
    try {
      Link existLink = findOneActive(id);

      if (!existLink.isHost(userId)) {
        throw new IllegalArgumentException("삭제 권한 없음"); //공통 예외?
      }

      existLink.setDeleteTf(true);
      linkRepository.save(existLink);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  public void changeCategoryIdByIds(List<Long> linkIds, Long newCategoryId, Long userId) throws Exception {
    try {
      Long privateShareCd = (long) 10; //getShareCode
      Long BuddyShareCd = (long) 20; //getShareCode

      Link link = linkRepository.findOneActive(linkIds.get(0));
      Long oldCategoryId = link.getCategoryId();
      Category oldCategory = categoryRepository.findCategoryById(oldCategoryId);
      Category newCategory = categoryRepository.findCategoryById(newCategoryId);
      Long oldBuddyId = oldCategory.getBuddyId();
      Long oldUserId = oldCategory.getUserId();

      Category existCategory;
      if (oldCategory.getShareTypeCd().equals(privateShareCd)) {
        if (!oldUserId.equals(userId)) {
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }
        categoryRepository.findExistPrivateCategory(newCategoryId, userId);
        linkRepository.changePrivateCategoryIdByIds(linkIds, newCategoryId, userId);

      } else {
        if (!oldBuddyId.equals(newCategory.getBuddyId())) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "같은 버디 내에서만 카테고리 이동 가능");
        }
        UserDTO.UserResponse existBuddyUser = buddyUserRepository.existBuddyUser(newCategory.getBuddyId(), userId);
        if (existBuddyUser == null) {
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }
        categoryRepository.findExistBuddyCategory(newCategoryId, oldCategory.getBuddyId(), userId);
        linkRepository.changeBuddyCategoryIdByIds(linkIds, newCategoryId);
      }

    } catch (Exception e) {
      System.out.println("e" + e);
      throw new Exception(e);
    }
  }

  public List<LinkDto.SearchResponse> searchLinks(Long userId, String keyword) throws Exception {
    try {
      List<LinkDto.SearchResponse> searchList = linkRepository.findLinksByKeyword(userId, keyword);
      return searchList;

    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);

    }
  }

  /**
   * 링크 preview image
   *
   * @param url
   * @return
   * @throws Exception
   */
  public Map<String, String> getLinkPreviewData(String url) throws Exception {
    Map<String, String> preview = new HashMap<>();
    try {
      // link preview
      // Jsoup으로 HTML 문서 가져오기 및 파싱
      Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
      // 메타데이터 추출
      String title = doc.title();
      String imageUrl = doc.select("meta[property=og:image]").attr("content");
      String description = doc.select("meta[name=description]").attr("content");
      // 첫번째 img 태그 선택하고, 태그 있는지 확인
      // img 태그의 src 속성에 있는 절대 URL 가져와서 absUrl은 상대 경로 URL을 절대 URL로 변환
      if (imageUrl.isEmpty()) {
        imageUrl = doc.select("img").first() != null ? doc.select("img").first().absUrl("src") : "";
      }

      // 응답 데이터
      preview.put("title", title);
      preview.put("imageUrl", imageUrl);
      preview.put("description", description);

      preview.forEach((key, value) -> {
        System.out.println(key + " : " + value);
      });
      return preview;

    } catch (Exception e) {
      preview.put("imageUrl", null);
      preview.put("title", "");
    }
    return preview;
  }
}
