package com.linkbuddy.domain.link.repository;

import com.linkbuddy.global.entity.Link;
import com.linkbuddy.global.entity.QCategory;
import com.linkbuddy.global.entity.QLink;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LinkCustomRepositoryImpl implements LinkCustomRepository {
  private final JPAQueryFactory query;
  QLink link = QLink.link;
  QCategory category = QCategory.category;

  @Override
  public List<Link> findAllActive() {
    List<Link> result = query.selectFrom(link).where(link.deleteTf.eq(false)).fetch();

    return result;
  }

  @Override
  public Link findOneActive(Long id) {
    Link result = query.selectFrom(link).where(link.id.eq(id)
            .and(link.deleteTf.eq(false))).fetchOne();
    if (result == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Link not found with id: " + id);
    }
    return result;
  }

  @Override
  public Long changePrivateCategoryIdByIds(List<Long> ids, Long newCategoryId, Long userId, Long privateShareCd) {
    long updatedLinks = query.update(link)
            .set(link.categoryId, newCategoryId)
            .where(link.categoryId.in(ids)
                    .and(JPAExpressions.select(category.shareTypeCd)
                            .from(category)
                            .where(category.id.eq(newCategoryId)).eq(privateShareCd))
                    .and(category.userId.eq(userId)))
            .execute();
    return updatedLinks;
  }

  @Override
  public Long changeBuddyCategoryIdByIds(List<Long> ids, Long oldBuddyId, Long newCategoryId, Long buddyShareCd) {

    long updatedLinks = query.update(link)
            .set(link.categoryId, newCategoryId)
            .where(link.categoryId.in(ids)
                    .and(JPAExpressions.select(category.buddyId) //query.selectFrom(category)
                            .from(category)
                            .where(category.id.eq(newCategoryId)).eq(oldBuddyId))
                    .and(JPAExpressions.select(category.shareTypeCd)
                            .from(category)
                            .where(category.id.eq(newCategoryId)).eq(buddyShareCd)))
            .execute();
    return updatedLinks;
  }

}