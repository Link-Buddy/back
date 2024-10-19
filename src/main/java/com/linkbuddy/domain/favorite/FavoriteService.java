package com.linkbuddy.domain.favorite;

import com.linkbuddy.domain.favorite.repository.FavoriteRepository;
import com.linkbuddy.domain.link.repository.LinkRepository;
import com.linkbuddy.global.entity.Favorite;
import com.linkbuddy.global.util.CustomException;
import com.linkbuddy.global.util.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.linkbuddy.domain.favorite
 * fileName       : FavoriteService
 * author         : yl951
 * date           : 2024-10-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-10-12        yl951       최초 생성
 */

@Service
public class FavoriteService {

  @Autowired
  private FavoriteRepository favoriteRepository;
  @Autowired
  private LinkRepository linkRepository;

  public void onOffFavorite(Long linkId, Long userId) throws Exception {
    try {
      Boolean isMine = linkRepository.isMyLink(linkId, userId);
      if (!isMine) {
        throw new CustomException(StatusEnum.NOT_FOUND, "not found link");
      }
      Boolean isExist = favoriteRepository.isExistFavoriteLink(linkId, userId);

      if (isExist) { //존재하면
        favoriteRepository.deleteFavoriteLink(linkId, userId);
      } else {
        Favorite newFavorite = Favorite.builder()
                .linkId(linkId)
                .userId(userId)
                .build();
        favoriteRepository.save(newFavorite);
      }

    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }


}
