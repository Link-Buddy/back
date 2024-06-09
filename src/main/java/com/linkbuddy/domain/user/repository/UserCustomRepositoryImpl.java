package com.linkbuddy.domain.user.repository;

import com.linkbuddy.global.entity.QUser;
import com.linkbuddy.global.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * packageName    : com.linkbuddy.domain.user.repository
 * fileName       : UserCustomRepositoryImpl
 * author         : admin
 * date           : 2024-04-20
 * description    : 회원 Repository Implements
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-20        admin       최초 생성
 */
@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {
  private final JPAQueryFactory query;
  QUser user = QUser.user;

  @Override
  public User customFindByEmail(String email) {
    User userData = query.selectFrom(user)
            .where(user.email.eq(email))
            .fetchOne();
    return userData;
  }
}