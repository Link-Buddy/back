package com.linkbuddy.domain.category;

import com.linkbuddy.domain.category.repository.CategoryRepository;
import com.linkbuddy.domain.link.repository.LinkRepository;
import com.linkbuddy.global.entity.Category;
import com.linkbuddy.global.util.CustomException;
import com.linkbuddy.global.util.StatusEnum;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.linkbuddy.domain.Category
 * fileName       : CategoryService
 * author         : yl951
 * date           : 2024-04-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-11        yl951       최초 생성
 */

@Service
public class CategoryService {
  @Autowired
  private CategoryRepository categoryRepository;
  private LinkRepository linkRepository;

  //유저 생성 시 기본으로 "미분류" 카테고리 생성
  public Category createGeneralCategory(CategoryDto.CreatePrivate privateGroupDto) throws Exception {
    try {

      Long userId = (long) 1234; //getUserId;
      Long shareType = (long) 10; //get ShearType


      Category generalPrivateCategory = Category.builder()
              .categoryName("미분류")
              .shareTypeCd(shareType)
              .userId(userId)
              .build();

      return categoryRepository.save(generalPrivateCategory);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  public Category createPrivateCategory(CategoryDto.CreatePrivate privateCategoryDto, Long userId) throws Exception {
    try {

      Long shareType = (long) 10; //get private ShearType

      Category existingCategory = categoryRepository.findPrivateCategoryByName(userId, shareType, privateCategoryDto.getCategoryName());
      if (existingCategory != null) {
        throw new CustomException(StatusEnum.CONFLICT, "이미 동일한 이름의 카테고리가 존재합니다.");
      }

      Category newPrivateCategory = Category.builder()
              .categoryName(privateCategoryDto.getCategoryName())
              .shareTypeCd(shareType)
              .userId(userId)
              .build();

      return categoryRepository.save(newPrivateCategory);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  public Category createBuddyCategory(CategoryDto.CreateBuddy buddyCategoryDto, Long userId) throws Exception {
    try {

      Long shareType = (long) 20; //get public ShearType

      Category newBuddyCategory = Category.builder()
              .categoryName(buddyCategoryDto.getCategoryName())
              .buddyId(buddyCategoryDto.getBuddyId())
              .shareTypeCd(shareType)
              .userId(userId)
              .build();

      return categoryRepository.save(newBuddyCategory);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  public List<CategoryDto.PrivateCategory> findMyPrivateCategories(Long userId) throws Exception {
    try {
      Long shareCode = (long) 10; //getShareCode
      return categoryRepository.findMyPrivateCategories(userId, shareCode);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }


  public List<CategoryDto.BuddyCategory> findMyBuddyCategoriesByBuddyId(Long buddyId, Long userId) throws Exception {
    try {
      Long shareCode = (long) 20; //getShareCode

      return categoryRepository.findMyBuddyCategoriesByBuddyId(userId, shareCode, buddyId);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  public Category updateCategory(Long id, CategoryDto.Update updateCategoryDto) throws Exception {
    try {

      Long userId = (long) 1; //getUserId;
      Category category = categoryRepository.findCategoryById(id);

      Category existCategory;
      if (category.getBuddyId() == null) {
        //개인
        existCategory = categoryRepository.findExistPrivateCategory(id, userId);
      } else {
        //버디
        existCategory = categoryRepository.findExistBuddyCategory(id, category.getBuddyId(), userId);
      }

      existCategory.updateCategory(updateCategoryDto);
      return categoryRepository.save(existCategory);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  public void deleteCategory(Long id) throws Exception {
    try {

      Long userId = (long) 1234; //getUserId;
      Category category = categoryRepository.findCategoryById(id);

      //개인
      Category existCategory;
      if (category.getBuddyId() == null) {
        existCategory = categoryRepository.findExistPrivateCategory(id, userId);
      } else {
        //버디
        existCategory = categoryRepository.findExistBuddyCategory(id, category.getBuddyId(), userId);
      }

      categoryRepository.deleteCategory(existCategory.getId());
    } catch (Exception e) {
      System.out.println("e" + e);
      throw new Exception(e);
    }
  }
}
