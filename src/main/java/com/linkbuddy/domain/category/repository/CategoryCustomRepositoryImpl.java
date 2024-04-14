package com.linkbuddy.domain.category.repository;

import com.linkbuddy.global.entity.QCategory;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName    : com.linkbuddy.domain.linkGroup.repository
 * fileName       : CategoryCustomRepositoryImpl
 * author         : yl951
 * date           : 2024-04-13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-13        yl951       최초 생성
 */
@Repository
@RequiredArgsConstructor
public class CategoryCustomRepositoryImpl implements CategoryCustomRepository {
  private final JPAQueryFactory query;

  QCategory category = QCategory.category;

//  @Override
//  public LinkGroup findActiveById(Long id) {
//    LinkGroup result = query.selectFrom(linkGroup).where(linkGroup.id.eq(id)
//            .and(linkGroup.deleteTf.eq(false))).fetchOne();
//    if (result == null) {
//      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Link not found with id: " + id);
//    }
//    return result;
//  }

  @Override
  public List<Tuple> findMyPrivateCategories(Long userId, Long shareTypeCd) {
    List<Tuple> result = query.select(category.id, category.groupName)
            .from(category)
            .where(category.userId.eq(userId).and(category.shareTypeCd.eq(shareTypeCd)).and(category.deleteTf.eq(false)))
            .fetch();

    return result;
  }

  @Override
  public List<Tuple> findMyBuddyCategories(Long shareTypeCd) {

    List<Tuple> result = query.select(category.id, category.groupName, category.buddyId)
            .from(category)
            .where(category.shareTypeCd.eq(shareTypeCd).and(category.deleteTf.eq(false)))
            .fetch();

    return result;
  }


}
