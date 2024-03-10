package com.linkbuddy.domain.link;

import com.linkbuddy.global.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface LinkRepository extends JpaRepository<Link, Long> {


  @Query("SELECT l FROM Link l WHERE l.id = :id AND l.deleteTf = false ")
  Optional<Link> findOneActive(Long id);
}
