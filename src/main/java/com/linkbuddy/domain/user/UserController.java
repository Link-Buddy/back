package com.linkbuddy.domain.user;

import com.linkbuddy.domain.user.dto.SignInDTO;
import com.linkbuddy.domain.user.dto.UserDTO;
import com.linkbuddy.global.config.jwt.JwtToken;
import com.linkbuddy.global.entity.User;
import com.linkbuddy.global.util.ResponseMessage;
import com.linkbuddy.global.util.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
  @Autowired
  UserService userService;

  /**
   * 회원 등록
   *
   * @param user
   * @return
   */
  @PostMapping("/join")
  public ResponseEntity createUser(User user) throws Exception {
    User savedUser = userService.create(user);
    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(savedUser)
            .build());
  }

  @GetMapping("/loginSuccess")
  @ResponseBody
  public ResponseEntity loginSuccess(@RequestParam("accessToken") String accessToken, @RequestParam("refreshToken") String refreshToken) {
    Map<String, Object> response = new HashMap<>();
    response.put("accessToken", accessToken);
    response.put("refreshToken", refreshToken);
    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(response)
            .build());

  }

  /**
   * 회원 조회
   *
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

  @PostMapping("/signIn")
  public JwtToken signIn(@RequestBody SignInDTO signInDTO) {
    String email = signInDTO.getEmail();
    String password = signInDTO.getPassword();

    JwtToken jwtToken = userService.signIn(email, password);
    log.info("req email = {}, password = {}", email, password);
    log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

    return jwtToken;
  }
}
