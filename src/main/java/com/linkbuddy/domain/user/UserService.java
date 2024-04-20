package com.linkbuddy.domain.user;

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

    public User create(User user) throws Exception {
        try {
            userRepository.save(user);
            return user;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public User find(String email) throws Exception {
        try {
            log.info("email = {}", email);
            return userRepository.findByEmail(email);
        } catch (Exception e) {
           throw new Exception();
        }
    }
}
