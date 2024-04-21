package com.linkbuddy.domain.buddyUser;

import com.linkbuddy.domain.buddy.dto.BuddyDTO;
import com.linkbuddy.domain.user.dto.UserDTO;
import com.linkbuddy.global.entity.BuddyUser;
import com.linkbuddy.global.util.ResponseMessage;
import com.linkbuddy.global.util.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : com.linkbuddy.domain.buddyUser
 * fileName       : BuddyUserController
 * author         : admin
 * date           : 2024-04-14
 * description    : 버디 회원 Controller
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-14        admin       최초 생성
 */
@Slf4j
@RestController
@RequestMapping("buddy/user")
public class BuddyUserController {
    @Autowired
    BuddyUserService buddyUserService;

    /**
     * 버디에 참여중인 회원 리스트
     * @param buddyId
     * @return
     * @throws Exception
     */
    @GetMapping
    public ResponseEntity getBuddyUserList(@RequestParam(value = "buddyId") Long buddyId) throws Exception {
        log.debug("buddyId = {}, buddyId");
        List<UserDTO.UserResponse> userList = buddyUserService.findUserAll(buddyId);
        return ResponseEntity.ok(ResponseMessage.builder()
                .status(StatusEnum.OK)
                .data(userList)
                .build());
    }

    /**
     * 버디 회원 생성 (친구초대)
     * @param buddy
     * @return
     * @throws Exception
     */
    @PostMapping
    public ResponseEntity createBuddyUser(BuddyDTO buddy) throws Exception {
        log.info("buddy = {}", buddy);
        BuddyUser savedBuddyUser = buddyUserService.createBuddyUser(buddy);
        return ResponseEntity.ok(ResponseMessage.builder()
                .status(StatusEnum.OK)
                .data(savedBuddyUser)
                .build());
    }

    /**
     * 회원 버디 수정 (알림설정 & 고정여부 & 초대수락여부)
     * @param id
     * @param buddy
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity updateBuddyUser(@PathVariable("id") Long id, @RequestBody BuddyDTO buddy) throws Exception {
        BuddyUser updateBuddyUser = buddyUserService.updateBuddyUser(id, buddy);
        return ResponseEntity.ok(ResponseMessage.builder()
                .status(StatusEnum.OK)
                .data(updateBuddyUser)
                .build());
    }

    /**
     * 회원 버디 탈퇴
     * @param buddyId
     * @param userId
     * @return
     * @throws Exception
     */
    @DeleteMapping
    public ResponseEntity deleteBuddyUser(@RequestParam(value = "buddyId") Long buddyId, @RequestParam(value = "userId") Long userId) throws Exception {
        Boolean deleteBuddyUser = buddyUserService.deleteBuddyUser(buddyId, userId);
        return ResponseEntity.ok(ResponseMessage.builder()
                .status(StatusEnum.OK)
                .data(deleteBuddyUser)
                .build());
    }
}
