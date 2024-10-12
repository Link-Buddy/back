package com.linkbuddy.domain.category.repository;

import com.linkbuddy.domain.category.CategoryDto;
import com.linkbuddy.domain.category.QCategoryDto_BuddyCategory;
import com.linkbuddy.domain.category.QCategoryDto_PrivateCategory;
import com.linkbuddy.global.entity.Category;
import com.linkbuddy.global.entity.QBuddyUser;
import com.linkbuddy.global.entity.QCategory;
import com.linkbuddy.global.entity.QLink;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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

  @PersistenceContext
  private EntityManager entityManager;
  QCategory category = QCategory.category;
  QBuddyUser buddyUser = QBuddyUser.buddyUser;
  QLink link = QLink.link;

  @Override
  public List<CategoryDto.PrivateCategory> findMyPrivateCategories(Long userId, Long shareTypeCd) {
    List<CategoryDto.PrivateCategory> result = query.select(new QCategoryDto_PrivateCategory(category, link.count()))
            .from(category)
            .leftJoin(link).on(link.categoryId.eq(category.id))
            .where(category.userId.eq(userId)
                    .and(category.shareTypeCd.eq(shareTypeCd))
                    .and(category.deleteTf.eq(false))
                    .and(link.deleteTf.eq(false)))
            .groupBy(category.id)
            .fetch();

    return result;
  }

  @Override
  public Category findPrivateCategoryByName(Long userId, Long shareTypeCd, String categoryName) {
    Category result = query.selectFrom(category)
            .where(category.userId.eq(userId)
                    .and(category.shareTypeCd.eq(shareTypeCd))
                    .and(category.categoryName.eq(categoryName))
                    .and(category.deleteTf.eq(false)))
            .fetchOne();

    return result;
  }


  @Override
  public List<CategoryDto.BuddyCategory> findMyBuddyCategoriesByBuddyId(Long userId, Long shareTypeCd, Long buddyId) {

    List<CategoryDto.BuddyCategory> result = query.select(new QCategoryDto_BuddyCategory(category, link.count()))
            .from(category)
            .leftJoin(buddyUser).on(category.buddyId.eq(buddyUser.buddyId))
            .leftJoin(link).on(link.categoryId.eq(category.id).and(link.deleteTf.eq(false)))
            .where(category.shareTypeCd.eq(shareTypeCd)
                    .and(category.deleteTf.eq(false))
                    .and(buddyUser.buddyId.eq(buddyId))
                    .and(buddyUser.userId.eq(userId)))
            .groupBy(category.id)
            .fetch();

    return result;
  }

  @Override
  public Category findExistBuddyCategory(Long id, Long buddyId, Long userId) {
    Category result = query.selectFrom(category)
            .leftJoin(buddyUser).on(category.buddyId.eq(buddyUser.buddyId))
            .where(category.id.eq(id)
                    .and(category.deleteTf.eq(false))
                    .and(buddyUser.buddyId.eq(buddyId))
                    .and(buddyUser.userId.eq(userId)))
            .fetchOne();

    if (result == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id);
    }

    return result;
  }


  @Override
  public Category findExistPrivateCategory(Long id, Long userId) {
    Category result = query.selectFrom(category)
            .where(category.id.eq(id)
                    .and(category.deleteTf.eq(false))
                    .and(category.userId.eq(userId))).fetchOne();

    if (result == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id);
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

  @Override
  @Transactional
  public void deleteCategory(Long id) {
    JPAUpdateClause updateCategory = query.update(category);
    updateCategory.set(category.deleteTf, true)
            .where(category.id.eq(id))
            .execute();

    JPAUpdateClause updateLink = query.update(link);
    updateLink.set(link.deleteTf, true)
            .where(link.categoryId.eq(id))
            .execute();


    entityManager.flush();
  }


}
