package com.linkbuddy.domain.buddyUser.repository;

import com.linkbuddy.domain.buddy.dto.BuddyDTO;
import com.linkbuddy.domain.user.dto.UserDTO;
import com.linkbuddy.global.entity.BuddyUser;

import java.util.List;

/**
 * packageName    : com.linkbuddy.domain.buddyUser.repository
 * fileName       : BuddyUserCustomRepository
 * author         : admin
 * date           : 2024-04-14
 * description    : 버디 회원 Repository
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-14        admin       최초 생성
 */
public interface BuddyUserCustomRepository {
    /** 버디 참여중인 회원 리스트 조회 (회원) */
    List<UserDTO.UserResponse> findUserByBuddyId(Long buddyId);
    /** 버디 회원 리스트 조회 (버디 삭제) */
    List<BuddyUser> findBuddyUsersByBuddyId(Long buddyId);
    /** 버디 회원 리스트 조회 (버디 회원 탈퇴)*/
    BuddyUser findBuddyUserByBuddyIdAndUserId(Long buddyId, Long userId);
}
