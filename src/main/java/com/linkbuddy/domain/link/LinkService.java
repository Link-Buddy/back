package com.linkbuddy.domain.link;

import com.linkbuddy.domain.category.repository.CategoryRepository;
import com.linkbuddy.domain.link.repository.LinkRepository;
import com.linkbuddy.global.entity.Category;
import com.linkbuddy.global.entity.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService {
  @Autowired
  private LinkRepository linkRepository;
  @Autowired
  private CategoryRepository categoryRepository;

  public List<Link> findAll() {
    return linkRepository.findAllActive();
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


  public Link save(LinkDto.Create linkDto) throws Exception {
    try {

      Long userId = (long) 1234; //getUserId;

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

      existLink.updateLink(linkDto);
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
      Long userId = (long) 1234; //getUserId;
      Long privateShareCd = (long) 10; //getShareCode
      Long BuddyShareCd = (long) 10; //getShareCode

      System.out.println(">>>>>>>>>>>>>>>>>>>>>" + linkIds.get(0));
      Link link = linkRepository.findOneActive(linkIds.get(0));
      Long oldCategoryId = link.getCategoryId();

      Category getCategory = categoryRepository.findCategoryById(oldCategoryId);
      Long oldBuddyId = getCategory.getBuddyId();

      System.out.println(">>>>>>>>>>>>>>>>>>>>>" + getCategory.getBuddyId() + getCategory.getId());
      Category existCategory;
      if (getCategory.getBuddyId() == null) {
        existCategory = categoryRepository.findExistPrivateCategory(newCategoryId, userId);
        if (existCategory != null) {
          linkRepository.changePrivateCategoryIdByIds(linkIds, newCategoryId, userId, privateShareCd);
        }
      } else {
        //버디
        existCategory = categoryRepository.findExistBuddyCategory(newCategoryId, getCategory.getBuddyId(), userId);
        if (existCategory != null) {
          linkRepository.changeBuddyCategoryIdByIds(linkIds, oldBuddyId, newCategoryId, BuddyShareCd);
        }
      }

    } catch (Exception e) {
      System.out.println("e" + e);
      throw new Exception(e);
    }
  }
}
