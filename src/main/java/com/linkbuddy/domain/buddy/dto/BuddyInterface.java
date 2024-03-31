package com.linkbuddy.domain.buddy.dto;

/**
 * packageName    : com.linkbuddy.domain.buddy.dto
 * fileName       : Buddy
 * author         : admin
 * date           : 2024-03-30
 * description    : buddy interface
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-30        admin       최초 생성
 */
public interface BuddyInterface {
    Long getBuddy_id();
    String getName();
    Boolean getAlert_tf();
    Boolean getPin_tf();
    String getCreated_at();
    String getUpdated_at();
}
