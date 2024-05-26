package com.linkbuddy.global.config;

import com.linkbuddy.domain.user.UserService;
import com.linkbuddy.global.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.linkbuddy.global.config
 * fileName       : PrincipalDetails
 * author         : admin
 * date           : 2024-04-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-27        admin       최초 생성
 */
@Component
@Slf4j
public class PrincipalDetails implements UserDetailsService {
    private final UserService userService;

    public PrincipalDetails(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userService.find(email);
            if (user == null) {
                throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
            }

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())   //비밀번호 체크는 알아서? 어떻게?
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
