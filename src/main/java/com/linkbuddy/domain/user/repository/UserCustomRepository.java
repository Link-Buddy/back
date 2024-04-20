package com.linkbuddy.domain.user.repository;

import com.linkbuddy.global.entity.User;

/**
 * packageName    : com.linkbuddy.domain.user.repository
 * fileName       : UserCustomRepository
 * author         : admin
 * date           : 2024-04-20
 * description    : 회원 Repository
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-20        admin       최초 생성
 */
public interface UserCustomRepository {
    User findByEmail(String email);
}
