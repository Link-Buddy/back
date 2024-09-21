package com.linkbuddy.domain.buddy.repository;

import com.linkbuddy.global.entity.Buddy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//@Transactional
public interface BuddyRepository extends JpaRepository<Buddy, Long>, BuddyCustomRepository {

  Optional<Buddy> findByNameAndCreatorId(String name, Long currentUserId);
}
