package com.linkbuddy.domain.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

/**
 * packageName    : com.linkbuddy.domain.user.dto
 * fileName       : UserDTO
 * author         : admin
 * date           : 2024-04-06
 * description    : 회원 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-06        admin       최초 생성
 */
@Data
public class UserDTO {
  private Long id;
  private String email;
  private String name;
  private String social;
  private Integer statusCd;
  private Long fileId;


  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class UserResponse {
    private Long id;
    private String name;
    private String email;

    @Builder
    @QueryProjection
    public UserResponse(Long id, String name, String email) {
      this.id = id;
      this.name = name;
      this.email = email;
    }
  }
}
