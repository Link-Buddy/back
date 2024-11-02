package com.linkbuddy.domain.link.repository;

import com.linkbuddy.domain.link.LinkDto;
import com.linkbuddy.global.entity.Link;
import com.querydsl.jpa.impl.JPAUpdateClause;

import java.util.List;

/**
 * packageName    : com.linkbuddy.domain.link.repository
 * fileName       : LinkCustomRepository
 * author         : yl951
 * date           : 2024-04-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-14        yl951       최초 생성
 */

public interface LinkCustomRepository {
  List<Link> findAllActive();

  List<LinkDto.LinkInfo> findMyLinksByCategoryId(Long categoryId, Long userId);

  List<Link> findBuddyLinksByCategoryId(Long categoryId);

  List<LinkDto.SearchResponse> getMyFavoriteLinks(Long userId);

  List<LinkDto.SearchResponse> getMyRegistedLinks(Long userId);

  Long findMyFavoriteCount(Long userId);

  Long findMyRegistedCount(Long userId);

  Link findOneActive(Long id);

  JPAUpdateClause changePrivateCategoryIdByIds(List<Long> ids, Long newCategoryId, Long userId);

  JPAUpdateClause changeBuddyCategoryIdByIds(List<Long> ids, Long newCategoryId);

  Boolean isMyLink(Long linkId, Long userId);

  //해당 폴더 내의 모든 링크들

  List<LinkDto.SearchResponse> findLinksByKeyword(Long userId, String keyword);

}
