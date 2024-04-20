package com.linkbuddy.domain.user.repository;

import com.linkbuddy.global.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//@Transactional
public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {

}
