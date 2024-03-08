package com.linkbuddy.domain.buddy.dto;

import lombok.Builder;
import lombok.Data;

/**
 * packageName    : com.linkbuddy.domain.buddy.dto
 * fileName       : BuddyDTO
 * author         : yjkim
 * date           : 2024-03-08
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-08        yjkim       최초 생성
 */
@Data
@Builder
public class BuddyDTO {
    private Long id;
    private String name;
    private Long userId;
    private Boolean alertTf;
    private Boolean pinTf;
}
