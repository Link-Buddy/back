package com.linkbuddy.domain.buddy.repository;

import com.linkbuddy.domain.buddy.dto.BuddyInterface;
import com.linkbuddy.domain.user.dto.UserInterface;
import com.linkbuddy.global.entity.BuddyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@Transactional
public interface BuddyUserRepository extends JpaRepository<BuddyUser, Long> {
    @Query(value = "SELECT b.buddy_id, b2.name, b.alert_tf, b.pin_tf, b.created_at, b.updated_at FROM BuddyUser b INNER JOIN Buddy b2 ON b.buddy_id = b2.id WHERE b.user_id = :userId AND b.accept_tf = true", nativeQuery = true)
    List<BuddyInterface> findBuddyById(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM buddyuser b WHERE b.buddy_id = :buddyId", nativeQuery = true)
    List<BuddyUser> findBuddyUsersByBuddyId(@Param("buddyId") Long buddyId);

    @Query(value = "SELECT u.id, u.name, u.email FROM BuddyUser b JOIN User u ON b.user_id = u.id WHERE b.buddy_id = :buddyId AND b.accept_tf = true", nativeQuery = true)
    List<UserInterface> findUserByBuddyId(@Param("buddyId") Long buddyId);

    @Query(value = "SELECT * FROM BuddyUser B WHERE B.buddy_id = :buddyId AND B.user_id = :userId", nativeQuery = true)
    BuddyUser findBuddyUserByBuddyIdAndUserId(@Param("buddyId") Long buddyId, @Param("userId") Long userId);
}
