package com.linkbuddy.domain.link.repository;

import com.linkbuddy.global.entity.Link;
import com.linkbuddy.global.entity.QCategory;
import com.linkbuddy.global.entity.QLink;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.transaction.Transactional;
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
  @Transactional
  public JPAUpdateClause changePrivateCategoryIdByIds(List<Long> ids, Long newCategoryId, Long userId) {

    JPAUpdateClause updatedLinks = query.update(link);
    updatedLinks.set(link.categoryId, newCategoryId)
            .where(link.id.in(ids).and(link.userId.eq(userId)))
            .execute();

    return updatedLinks;
  }

  @Override
  @Transactional
  public JPAUpdateClause changeBuddyCategoryIdByIds(List<Long> ids, Long newCategoryId) {

    JPAUpdateClause updatedLinks = query.update(link);
    updatedLinks
            .set(link.categoryId, newCategoryId)
            .where(link.id.in(ids))
            .execute();
    return updatedLinks;
  }

}