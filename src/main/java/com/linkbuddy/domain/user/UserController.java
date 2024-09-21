package com.linkbuddy.domain.user;

import com.linkbuddy.domain.user.dto.ChangePasswordDto;
import com.linkbuddy.domain.user.dto.SignInDTO;
import com.linkbuddy.domain.user.dto.UserDTO;
import com.linkbuddy.global.config.jwt.JwtToken;
import com.linkbuddy.global.config.jwt.SecurityUtil;
import com.linkbuddy.global.entity.User;
import com.linkbuddy.global.util.ResponseMessage;
import com.linkbuddy.global.util.StatusEnum;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
  @Autowired
  SecurityUtil securityUtil;

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
      return ResponseEntity.ok(ResponseMessage.builder()
              .status(StatusEnum.NOT_FOUND)
              .build());
    } else {
      HashMap<String, Object> userData = new HashMap<>();
      userData.put("id", user.getId());
      userData.put("name", user.getName());
      userData.put("email", user.getEmail());

      return ResponseEntity.ok(ResponseMessage.builder()
              .status(StatusEnum.OK)
              .data(userData)
              .build());
    }
  }

  /**
   * 내 정보
   *
   * @return
   * @throws Exception
   */
  @GetMapping("/my")
  public ResponseEntity getUser() throws Exception {
    Long userId = securityUtil.getCurrentUserId();
    User myInfo = userService.findById(userId);
    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(myInfo)
            .build());
  }


  /**
   * 내 정보 수정
   *
   * @return
   * @throws Exception
   */
  @PatchMapping("/my")
  public ResponseEntity updateUser(@RequestBody UserDTO.Update updateUserDto) throws Exception {
    Long userId = securityUtil.getCurrentUserId();
    User updatedUser = userService.updateUser(userId, updateUserDto);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(updatedUser)
            .build());
  }


  /**
   * 비밀번호 변경
   *
   * @return
   * @throws Exception
   */
  @PatchMapping("/my/change-password")
  public ResponseEntity getUser(@RequestBody ChangePasswordDto changePasswordDto) throws Exception {
    Long userId = securityUtil.getCurrentUserId();
    boolean success = userService.changePassword(userId, changePasswordDto.getCurrentPassword(), changePasswordDto.getNewPassword());

    if (success) {
      return ResponseEntity.ok(
              ResponseMessage.builder()
                      .status(StatusEnum.OK)
                      .message("비밀번호 변경이 완료되었습니다.")
                      .build()
      );
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(ResponseMessage.builder()
                      .status(StatusEnum.INTERNAL_SERVER_ERROR)
                      .message("비밀번호 변경에 실패하였습니다.")
                      .build());
    }

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
