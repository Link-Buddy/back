package com.linkbuddy.domain.link.repository;

import com.linkbuddy.global.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface LinkRepository extends JpaRepository<Link, Long>, LinkCustomRepository {


//  @Query("SELECT l FROM Link l WHERE l.id = :id AND l.deleteTf = false ")
//  Optional<Link> findOneActive(Long id);

//  @Query("SELECT l FROM Link l WHERE l.id = :id AND l.deleteTf = false AND l.userId = :userId")
//  Optional<Link> findMyOneActive(Long id, Long userId);
}
