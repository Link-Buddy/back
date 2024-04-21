package com.linkbuddy.domain.category;

import com.linkbuddy.domain.category.repository.CategoryRepository;
import com.linkbuddy.global.entity.Category;
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

  public Category createPrivateCategory(CategoryDto.CreatePrivate privateCategoryDto) throws Exception {
    try {

      Long userId = (long) 1234; //getUserId;
      Long shareType = (long) 10; //get private ShearType

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

  public Category createBuddyCategory(CategoryDto.CreatePublic publicCategoryDto) throws Exception {
    try {

      Long userId = (long) 1234; //getUserId;
      Long shareType = (long) 20; //get public ShearType

      Category newPublicCategory = Category.builder()
              .categoryName(publicCategoryDto.getCategoryName())
              .buddyId(publicCategoryDto.getBuddyId())
              .shareTypeCd(shareType)
              .userId(userId)
              .build();

      return categoryRepository.save(newPublicCategory);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  public List<Tuple> findMyPrivateCategories() throws Exception {
    try {
      Long userId = (long) 1234; //getUserId;
      Long shareCode = (long) 10;//getShareCode
      return categoryRepository.findMyPrivateCategories(userId, shareCode);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  public List<Tuple> findMyBuddyCategories() throws Exception {
    try {
      Long userId = (long) 1234; //getUserId;
      Long shareCode = (long) 20; //getShareCode

      //TODO: 해당 buddy에 해당 user가 포함되어있느지도 확인해야함
      return categoryRepository.findMyBuddyCategories(shareCode);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  //TODO: [get] 특정 버디 들어갔을때 해당하는 폴더들 조회

  public Category updateCategory(Long id, CategoryDto.Update updateCategoryDto) throws Exception {
    try {

      Long userId = (long) 1234; //getUserId;
      Category category = categoryRepository.findCategoryById(id);

      //개인
      Category existCategory;
      if (category.getBuddyId() == null) {
        existCategory = categoryRepository.findExistPrivateCategory(updateCategoryDto, userId);
      } else {
        //버디
        existCategory = categoryRepository.findExistBuddyCategory(updateCategoryDto, category.getBuddyId(), userId);
      }

      existCategory.updateCategory(updateCategoryDto);
      return categoryRepository.save(existCategory);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }
  //TODO: 삭제 시 내부 링크들도 전체 삭제됨

}
