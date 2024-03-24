package com.linkbuddy.domain.user;

import com.linkbuddy.domain.user.repository.UserRepository;
import com.linkbuddy.global.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        userRepository.save(user);
        return user;
    }
}
