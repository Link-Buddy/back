package com.linkbuddy.domain.user;

import com.linkbuddy.domain.user.dto.UserDTO;
import com.linkbuddy.domain.user.dto.UserInterface;
import com.linkbuddy.global.entity.User;
import com.linkbuddy.global.util.ResponseMessage;
import com.linkbuddy.global.util.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity saveUser(User user) {
        User savedUser = userService.save(user);
        return ResponseEntity.ok(ResponseMessage.builder()
                .status(StatusEnum.OK)
                .data(savedUser)
                .build());
    }

    @GetMapping()
    public ResponseEntity getUser(@RequestParam(value = "email") String email) {
        try {
            UserInterface user = userService.find(email);
            log.info("user data = {}", user);

            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(ResponseMessage.builder()
                    .status(StatusEnum.OK)
                    .data(user)
                    .build());

        } catch(Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
