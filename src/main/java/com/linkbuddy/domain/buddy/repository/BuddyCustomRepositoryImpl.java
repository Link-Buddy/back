package com.linkbuddy.domain.buddy.repository;

import com.linkbuddy.domain.buddy.dto.BuddyDTO;
import com.linkbuddy.domain.buddy.dto.QBuddyDTO_BuddyResponse;
import com.linkbuddy.domain.buddyUser.repository.BuddyUserCustomRepository;
import com.linkbuddy.global.entity.QBuddy;
import com.linkbuddy.global.entity.QBuddyUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName    : com.linkbuddy.domain.buddy.repository
 * fileName       : BuddyCustomRepositoryImpl
 * author         : admin
 * date           : 2024-04-14
 * description    : 버디 Repository Implements
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-14        admin       최초 생성
 */
@Repository
@RequiredArgsConstructor
public class BuddyCustomRepositoryImpl implements BuddyCustomRepository {
    private final JPAQueryFactory query;
    QBuddy buddy = QBuddy.buddy;
    QBuddyUser buddyUser = QBuddyUser.buddyUser;

    @Override
    public List<BuddyDTO.BuddyResponse> findBuddyByUserId(Long userId) {
        List<BuddyDTO.BuddyResponse> buddyList = query.select(new QBuddyDTO_BuddyResponse(buddyUser.id, buddyUser.buddyId, buddy.name, buddyUser.alertTf, buddyUser.pinTf))
                .from(buddyUser)
                .join(buddyUser.buddy, buddy)
                .on(buddyUser.buddyId.eq(buddy.id))
                .where(buddyUser.userId.eq(userId).and(buddyUser.acceptTf.eq(true)))
                .fetch();
        return buddyList;
    }
}
