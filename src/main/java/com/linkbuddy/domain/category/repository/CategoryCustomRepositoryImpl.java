package com.linkbuddy.domain.category.repository;

import com.linkbuddy.domain.category.CategoryDto;
import com.linkbuddy.global.entity.Category;
import com.linkbuddy.global.entity.QBuddyUser;
import com.linkbuddy.global.entity.QCategory;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

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
  QBuddyUser buddyUser = QBuddyUser.buddyUser;

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

  @Override
  public Category findExistBuddyCategory(CategoryDto.Update updateDto, Long buddyId, Long userId) {
    Category result = (Category) query.select(category.id, category.groupName, category.updatedAt, category.fileId)
            .from(category)
            .leftJoin(buddyUser)
            .where(category.id.eq(updateDto.getId())
                    .and(category.deleteTf.eq(false))
                    .and(buddyUser.buddyId.eq(buddyId))
                    .and(buddyUser.userId.eq(userId))).fetchOne();

    if (result == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + updateDto.getId());
    }

    return result;
  }


  @Override
  public Category findExistPrivateCategory(CategoryDto.Update updateDto, Long userId) {
    Category result = (Category) query.select(category.id, category.groupName, category.updatedAt, category.fileId)
            .from(category)
            .where(category.id.eq(updateDto.getId())
                    .and(category.deleteTf.eq(false))
                    .and(category.userId.eq(userId))).fetchOne();

    if (result == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + updateDto.getId());
    }

    return result;
  }

  @Override
  public Category findCategoryById(Long id) {
    Category result = query.selectFrom(category)
            .where(category.id.eq(id).and(category.deleteTf.eq(false))).fetchOne();
    if (result == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id);
    }
    return result;
  }


}
