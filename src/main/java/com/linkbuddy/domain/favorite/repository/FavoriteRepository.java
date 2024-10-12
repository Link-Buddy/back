package com.linkbuddy.domain.favorite.repository;

import com.linkbuddy.domain.buddyUser.repository.BuddyUserCustomRepository;
import com.linkbuddy.global.entity.BuddyUser;
import com.linkbuddy.global.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName    : com.linkbuddy.domain.favorite.repository
 * fileName       : FavoriteRepository
 * author         : yl951
 * date           : 2024-10-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-10-12        yl951       최초 생성
 */
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long>, FavoriteCustomRepository {

}
