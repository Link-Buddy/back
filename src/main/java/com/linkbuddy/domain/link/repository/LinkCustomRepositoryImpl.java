package com.linkbuddy.domain.link.repository;

import com.linkbuddy.domain.category.CategoryDto;
import com.linkbuddy.domain.link.LinkDto;
import com.linkbuddy.domain.link.QLinkDto_SearchResponse;
import com.linkbuddy.domain.link.QLinkDto_Mylink;
import com.linkbuddy.global.entity.*;
import com.linkbuddy.domain.category.CategoryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class LinkCustomRepositoryImpl implements LinkCustomRepository {
  private final JPAQueryFactory query;
  QLink link = QLink.link;
  QCategory category = QCategory.category;
  QBuddy buddy = QBuddy.buddy;
  QBuddyUser buddyUser = QBuddyUser.buddyUser;
  QFavorite favorite = QFavorite.favorite;

  @Override
  public List<Link> findAllActive() {
    List<Link> result = query.selectFrom(link).where(link.deleteTf.eq(false)).fetch();

    return result;
  }

  @Override
  public List<LinkDto.LinkInfo> findMyLinksByCategoryId(Long categoryId, Long userId) {
    List<LinkDto.LinkInfo> result = query.select(link, favorite.id.isNotNull().as("isFavorite"))
            .from(link)
            .leftJoin(favorite).on(link.id.eq(favorite.linkId)
                    .and((favorite.userId.eq(userId))))
            .where(link.deleteTf.eq(false)
                    .and(link.categoryId.eq(categoryId))
                    .and(link.userId.eq(userId)))
            .fetch()
            .stream()
            .map(tuple -> {
              Link linkEntity = tuple.get(0, Link.class);
              Boolean isFavorite = tuple.get(1, Boolean.class);
              return new LinkDto.LinkInfo(linkEntity, isFavorite);
            })
            .collect(Collectors.toList());
    return result;
  }

  @Override
  public List<Link> findBuddyLinksByCategoryId(Long categoryId) {
    List<Link> result = query.selectFrom(link).where(link.deleteTf.eq(false).and(link.categoryId.eq(categoryId))).fetch();

    return result;
  }

  @Override
  public List<Link> getMyFavoriteLinks(Long userId) {
    List<Link> result = query.selectFrom(link)
            .leftJoin(favorite).on(link.id.eq(favorite.linkId))
            .where(favorite.userId.eq(userId).and(link.deleteTf.eq(false)))
            .fetch();

    return result;
  }

  @Override
  public Long findMyLinkCount(Long userId) {
    Long result = query.select(link.count())
            .from(link)
            .where(link.deleteTf.eq(false).and(link.userId.eq(userId)))
            .fetchOne();

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

  @Override
  public Boolean isMyLink(Long linkId, Long userId) {
    return query.selectOne()
            .from(link)
            .leftJoin(category).on(link.categoryId.eq(category.id))
            .leftJoin(buddyUser).on(buddyUser.buddyId.eq(category.buddyId))
            .where(link.id.eq(linkId)
                    .and(link.userId.eq(userId)
                            .or(buddyUser.userId.eq(userId))))
            .fetchFirst() != null;
  }


  @Override
  public List<LinkDto.SearchResponse> findLinksByKeyword(Long userId, String keyword) {
    List<LinkDto.SearchResponse> result = query.select(new QLinkDto_SearchResponse(
            category.categoryName,
            category.shareTypeCd,
            buddy.name,
            link.name,
            link.description,
            link.linkUrl
    ))
            .from(link)
            .innerJoin(link.category, category)
            .leftJoin(category.buddy, buddy)
            .leftJoin(buddyUser)
            .on(buddyUser.buddy.id.eq(buddy.id)
                    .and(buddyUser.userId.eq(userId))
                    .and(buddyUser.acceptTf.isTrue()))
            .where(link.userId.eq(userId).and(link.deleteTf.isFalse()).and(link.name.contains(keyword).or(link.description.contains(keyword))))
            .fetch();

    return result;
  }


}