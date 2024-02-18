package com.linkbuddy.domain.buddy.repository;

import com.linkbuddy.global.entity.Buddy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuddyRepository extends JpaRepository<Buddy, Integer> {
}
