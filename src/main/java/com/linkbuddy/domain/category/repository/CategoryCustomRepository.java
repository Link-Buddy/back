package com.linkbuddy.domain.category.repository;

import com.linkbuddy.domain.category.CategoryDto;
import com.linkbuddy.global.entity.Category;
import com.querydsl.core.Tuple;

import java.util.List;

/**
 * packageName    : com.linkbuddy.domain.linkGroup.repository
 * fileName       : CategoryCustomRepository
 * author         : yl951
 * date           : 2024-04-13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-13        yl951       최초 생성
 */
public interface CategoryCustomRepository {


  List<CategoryDto.PrivateCategory> findMyPrivateCategories(Long userId, Long shareTypeCd);

  Category findPrivateCategoryByName(Long userId, Long shareTypeCd, String categoryName);

  List<CategoryDto.BuddyCategory> findMyBuddyCategoriesByBuddyId(Long userId, Long shareTypeCd, Long buddyId);

  //권한 있는 카테고리
  Category findExistBuddyCategory(Long id, Long buddyId, Long userId);

  Category findExistPrivateCategory(Long id, Long userId);

  Category findCategoryById(Long id);

  void deleteCategory(Long id);
}
