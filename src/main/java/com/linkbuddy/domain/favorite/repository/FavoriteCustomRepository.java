package com.linkbuddy.domain.favorite.repository;

/**
 * packageName    : com.linkbuddy.domain.favorite.repository
 * fileName       : FavoriteCustomRepository
 * author         : yl951
 * date           : 2024-10-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-10-12        yl951       최초 생성
 */
public interface FavoriteCustomRepository {
  Long findMyFavoriteCount(Long userId);
}
