package com.linkbuddy.domain.buddy.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : com.linkbuddy.domain.buddy.dto
 * fileName       : BuddyUpdateDTO
 * author         : admin
 * date           : 2024-03-23
 * description    : Buddy update DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-23        admin       최초 생성
 */
@Getter
public class BuddyUpdateDTO {
    private String name;

    @Builder
    public BuddyUpdateDTO(String name) {
        this.name = name;
    }
}
