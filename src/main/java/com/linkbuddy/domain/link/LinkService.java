package com.linkbuddy.domain.link;

import com.linkbuddy.domain.buddyUser.repository.BuddyUserRepository;
import com.linkbuddy.domain.category.repository.CategoryRepository;
import com.linkbuddy.domain.link.repository.LinkRepository;
import com.linkbuddy.domain.user.dto.UserDTO;
import com.linkbuddy.global.entity.Category;
import com.linkbuddy.global.entity.Link;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class LinkService {
  @Autowired
  private LinkRepository linkRepository;
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private BuddyUserRepository buddyUserRepository;

  public List<Link> findAll() {
    return linkRepository.findAllActive();
  }

  public List<Link> findMyByCategoryId(Long categoryId, Long userId) throws Exception {
    try {
      return linkRepository.findMyLinksByCategoryId(categoryId, userId);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  public List<Link> findBuddyLinksByCategoryId(Long categoryId, Long userId, Long buddyId) throws Exception {
    try {
      //TODO : 해당 유저가 특정 buddy에 포함되는지 확인

      return linkRepository.findBuddyLinksByCategoryId(categoryId);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
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

  public void changeCategoryIdByIds(List<Long> linkIds, Long newCategoryId) throws Exception {
    try {
      Long userId = (long) 1; //getUserId;
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
}
