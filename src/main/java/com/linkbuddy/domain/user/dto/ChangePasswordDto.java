package com.linkbuddy.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.linkbuddy.domain.user.dto
 * fileName       : ChangePasswordDto
 * author         : yl951
 * date           : 2024-09-17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-09-17        yl951       최초 생성
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {

  @NotBlank(message = "현재 비밀번호를 입력해 주세요.")
  private String currentPassword;

  @NotBlank(message = "새 비밀번호를 입력해 주세요.")
  private String newPassword;
}
