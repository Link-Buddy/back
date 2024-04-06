package com.linkbuddy.domain.user;

import com.linkbuddy.domain.user.dto.UserDTO;
import com.linkbuddy.domain.user.dto.UserInterface;
import com.linkbuddy.domain.user.repository.UserRepository;
import com.linkbuddy.global.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    public UserInterface find(String email) {
        log.info("email = {}", email);
        return userRepository.findByEmail(email);
    }
}
