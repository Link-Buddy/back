package com.linkbuddy.domain.favorite;

import com.linkbuddy.global.config.jwt.SecurityUtil;

import com.linkbuddy.global.entity.Favorite;
import com.linkbuddy.global.util.ResponseMessage;
import com.linkbuddy.global.util.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.linkbuddy.domain.favorite
 * fileName       : FavoriteController
 * author         : yl951
 * date           : 2024-10-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-10-12        yl951       최초 생성
 */

@RequestMapping("favorites")
@RestController
public class FavoriteController {
  @Autowired
  SecurityUtil securityUtil;
  @Autowired
  private FavoriteService favoriteService;

  @PostMapping
  public ResponseEntity onOffFavorite(@RequestParam("linkId") Long linkId) throws Exception {

    Long userId = securityUtil.getCurrentUserId();
    favoriteService.onOffFavorite(linkId, userId);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK).data("")
            .build());
  }

}
