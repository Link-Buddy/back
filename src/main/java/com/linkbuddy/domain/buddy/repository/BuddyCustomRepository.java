package com.linkbuddy.domain.buddy.repository;

import com.linkbuddy.domain.buddy.dto.BuddyDTO;

import java.util.List;

/**
 * packageName    : com.linkbuddy.domain.buddy.repository
 * fileName       : BuddyCustomRepository
 * author         : admin
 * date           : 2024-04-14
 * description    : 버디 Repository
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-14        admin       최초 생성
 */
public interface BuddyCustomRepository {
    /** 버디 리스트 조회 (by userId) */
    List<BuddyDTO.BuddyResponse> findBuddyByUserId(Long userId);
}
