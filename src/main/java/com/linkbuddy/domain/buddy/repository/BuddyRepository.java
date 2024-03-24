package com.linkbuddy.domain.buddy.repository;

import com.linkbuddy.domain.buddy.dto.BuddyDTO;
import com.linkbuddy.global.entity.Buddy;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//@Transactional
public interface BuddyRepository extends JpaRepository<Buddy, Long> {

    Optional<Buddy> findById(Long id);

    // 쿼리 직접 실행
    @Query("SELECT b FROM Buddy b WHERE b.name LIKE :name")
    List<Buddy> findByName(@Param("name") String name);
}
