package com.linkbuddy.domain.user.dto;

import lombok.Builder;
import lombok.Data;

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
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String social;
    private Integer statusCd;
    private Long fileId;
}
