package com.linkbuddy.domain.user;

import com.linkbuddy.global.entity.User;
import com.linkbuddy.global.util.ResponseMessage;
import com.linkbuddy.global.util.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 회원 등록
     * @param user
     * @return
     */
    @PostMapping
    public ResponseEntity createUser(User user) throws Exception{
        User savedUser = userService.create(user);
        return ResponseEntity.ok(ResponseMessage.builder()
                .status(StatusEnum.OK)
                .data(savedUser)
                .build());
    }

    /**
     * 회원 조회
     * @param email
     * @return
     * @throws Exception
     */
    @GetMapping
    public ResponseEntity getUser(@RequestParam(value = "email") String email) throws Exception {
        User user = userService.find(email);
        log.info("user data = {}", user);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ResponseMessage.builder()
                .status(StatusEnum.OK)
                .data(user)
                .build());
    }
}
