package com.linkbuddy.domain.category.repository;

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

}
