package com.linkbuddy.domain.link.repository;

import com.linkbuddy.global.entity.Link;

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

  Link findOneActive(Long id);

  Long changePrivateCategoryIdByIds(List<Long> ids, Long newCategoryId, Long userId, Long privateShareCd);

  Long changeBuddyCategoryIdByIds(List<Long> ids, Long oldBuddyId, Long newCategoryId, Long buddyId);


  //해당 폴더 내의 모든 링크들

}
