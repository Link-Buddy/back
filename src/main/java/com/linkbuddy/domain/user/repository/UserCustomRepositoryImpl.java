package com.linkbuddy.domain.user.repository;

import com.linkbuddy.global.entity.QUser;
import com.linkbuddy.global.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

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

  @Override
  public User update(Long userId, String name) {
    User existingUser = query.selectFrom(user)
            .where(user.id.eq(userId))
            .fetchOne();

    if (existingUser == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId);

    }
    // 2. 찾은 사용자의 이름을 업데이트한다
    existingUser.setName(name);

    // 3. 업데이트 쿼리를 실행한다
    query.update(user)
            .where(user.id.eq(userId))
            .set(user.name, name)
            .execute();

    System.out.println(">>>>>>existingUser>>>>>>" + existingUser);
    // 4. 변경된 객체를 반환한다
    return existingUser;

  }
}