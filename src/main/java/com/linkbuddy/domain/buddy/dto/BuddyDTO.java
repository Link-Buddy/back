package com.linkbuddy.domain.buddy.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.sql.Timestamp;

/**
 * packageName    : com.linkbuddy.domain.buddy.dto
 * fileName       : BuddyDTO
 * author         : yjkim
 * date           : 2024-03-08
 * description    : BuddyDTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-08        yjkim       최초 생성
 */
@Data
public class BuddyDTO {
    private Long id;
    private String name;
    private Long userId;
    private Long buddyId;
    private Boolean alertTf;
    private Boolean pinTf;
    private Boolean acceptTf;
    private Timestamp acceptDt;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BuddyResponse {
        private Long id;
        private String name;
        private Long buddyId;
        private Boolean alertTf;
        private Boolean pinTf;

        @Builder
        @QueryProjection
        public BuddyResponse(Long id, Long buddyId, String name, Boolean alertTf, Boolean pinTf) {
            this.id = id;
            this.buddyId = buddyId;
            this.name = name;
            this.alertTf = alertTf;
            this.pinTf = pinTf;
        }
    }


}
