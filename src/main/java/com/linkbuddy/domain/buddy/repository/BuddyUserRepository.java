package com.linkbuddy.domain.buddy.repository;

import com.linkbuddy.domain.buddy.dto.BuddyDTO;
import com.linkbuddy.global.entity.Buddy;
import com.linkbuddy.global.entity.BuddyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@Transactional
public interface BuddyUserRepository extends JpaRepository<BuddyUser, Long> {
    @Query(value = "SELECT b.buddy_id, b2.name, b.alert_tf, b.pin_tf, b.created_at, b.updated_at FROM BuddyUser b INNER JOIN Buddy b2 ON b.buddy_id = b2.id WHERE b.user_id = :userId", nativeQuery = true)
    List<BuddyDTO> findBuddyById(@Param("userId") Integer userId);

    @Query(value = "SELECT * FROM buddyuser b WHERE b.buddy_id = :buddyId", nativeQuery = true)
    List<BuddyUser> findBuddyUsersByBuddyId(@Param("buddyId") Long buddyId);
}
