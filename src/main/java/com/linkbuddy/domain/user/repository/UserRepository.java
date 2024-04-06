package com.linkbuddy.domain.user.repository;

import com.linkbuddy.domain.user.dto.UserDTO;
import com.linkbuddy.domain.user.dto.UserInterface;
import com.linkbuddy.global.entity.Buddy;
import com.linkbuddy.global.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM User U WHERE U.email = :email", nativeQuery = true)
    UserInterface findByEmail(@Param("email") String email);
}
