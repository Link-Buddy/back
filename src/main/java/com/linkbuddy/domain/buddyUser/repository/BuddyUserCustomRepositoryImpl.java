package com.linkbuddy.domain.buddyUser.repository;

import com.linkbuddy.domain.user.dto.QUserDTO_UserResponse;
import com.linkbuddy.domain.user.dto.UserDTO;
import com.linkbuddy.global.entity.BuddyUser;
import com.linkbuddy.global.entity.QBuddyUser;
import com.linkbuddy.global.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName    : com.linkbuddy.domain.buddyUser.repository
 * fileName       : BuddyUserCustomRepositoryImpl
 * author         : admin
 * date           : 2024-04-14
 * description    : 버디 회원 Repository Implements
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-14        admin       최초 생성
 */
@Repository
@RequiredArgsConstructor
public class BuddyUserCustomRepositoryImpl implements BuddyUserCustomRepository {
  private final JPAQueryFactory query;
  QBuddyUser buddyUser = QBuddyUser.buddyUser;
  QUser user = QUser.user;

  @Override
  public List<UserDTO.UserResponse> findUserByBuddyId(Long buddyId) {
    List<UserDTO.UserResponse> userList = query.select(new QUserDTO_UserResponse(user.id, user.name, user.email))
            .from(buddyUser)
            .leftJoin(buddyUser.user, user)
            .on(buddyUser.userId.eq(user.id))
            .where(buddyUser.buddyId.eq(buddyId).and(buddyUser.acceptTf.eq(true)))
            .fetch();
    return userList;
  }

  @Override
  public UserDTO.UserResponse existBuddyUser(Long buddyId, Long userId) {
    UserDTO.UserResponse existResult = query.select(new QUserDTO_UserResponse(user.id, user.name, user.email))
            .from(buddyUser)
            .leftJoin(buddyUser.user, user)
            .on(buddyUser.userId.eq(user.id))
            .where(buddyUser.buddyId.eq(buddyId).and(buddyUser.userId.eq(userId)))
            .fetchOne();
    return existResult;
  }

  @Override
  public List<BuddyUser> findBuddyUsersByBuddyId(Long buddyId) {
    List<BuddyUser> buddyUserList = query.selectFrom(buddyUser)
            .where(buddyUser.buddyId.eq(buddyId)).fetch();
    return buddyUserList;
  }

  @Override
  public BuddyUser findBuddyUserByBuddyIdAndUserId(Long buddyId, Long userId) {
    BuddyUser buddyUserResult = query.selectFrom(buddyUser)
            .where(buddyUser.buddyId.eq(buddyId).and(buddyUser.userId.eq(userId)))
            .fetchOne();
    return buddyUserResult;
  }

}
