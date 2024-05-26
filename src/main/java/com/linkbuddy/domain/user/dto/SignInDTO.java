package com.linkbuddy.domain.user.dto;

import lombok.Data;

/**
 * packageName    : com.linkbuddy.domain.user.dto
 * fileName       : SignInDTO
 * author         : admin
 * date           : 2024-05-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-18        admin       최초 생성
 */
@Data
public class SignInDTO {
    private String email;
    private String password;
}
