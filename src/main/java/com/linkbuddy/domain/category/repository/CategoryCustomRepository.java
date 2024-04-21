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


  List<Tuple> findMyPrivateCategories(Long userId, Long shareTypeCd);

  List<Tuple> findMyBuddyCategories(Long shareTypeCd);

  //권한 있는 카테고리
  Category findExistBuddyCategory(CategoryDto.Update updateDto, Long buddyId, Long userId);


  Category findExistPrivateCategory(CategoryDto.Update updateDto, Long userId);

  Category findCategoryById(Long id);

}
