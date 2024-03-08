package com.linkbuddy.domain.buddy.repository;

import com.linkbuddy.global.entity.Buddy;
import com.linkbuddy.global.entity.BuddyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//@Transactional
public interface BuddyUserRepository extends JpaRepository<BuddyUser, Long> {

}
