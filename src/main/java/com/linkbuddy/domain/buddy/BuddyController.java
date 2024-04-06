package com.linkbuddy.domain.buddy;

import com.linkbuddy.domain.buddy.dto.BuddyDTO;
import com.linkbuddy.domain.buddy.dto.BuddyInterface;
import com.linkbuddy.domain.user.dto.UserInterface;
import com.linkbuddy.global.entity.Buddy;
import com.linkbuddy.global.entity.BuddyUser;
import com.linkbuddy.global.util.ResponseMessage;
import com.linkbuddy.global.util.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("buddy")
public class BuddyController {
    @Autowired
    BuddyService buddyService;

    /**
     * 회원 버디 리스트 조회
     * @param userId
     * @return
     */
    @GetMapping
    public ResponseEntity getBuddyList(@RequestParam(value = "userId") Long userId) {
        log.debug("userId = {}", userId);
        List<BuddyInterface> buddyList = buddyService.findAll(userId);
        return ResponseEntity.ok(ResponseMessage.builder()
                .status(StatusEnum.OK)
                .data(buddyList)
                .build());
    }

    /**
     * 버디에 참여중인 회원 리스트
     * @param buddyId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/user")
    public ResponseEntity getBuddyUserList(@RequestParam(value = "buddyId") Long buddyId) throws Exception {
        log.debug("buddyId = {}, buddyId");
        List<UserInterface> userList = buddyService.findUserAll(buddyId);
        return ResponseEntity.ok(ResponseMessage.builder()
                .status(StatusEnum.OK)
                .data(userList)
                .build());
    }

    /**
     * 버디 상세 조회
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getBuddy(@PathVariable("id")Long id) {
        try {
            log.info("info log = {}", id);
            Optional<Buddy> buddy = buddyService.findById(id);
            log.info("buddy = {}", buddy);
            if (buddy.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(ResponseMessage.builder()
                    .status(StatusEnum.OK)
                    .data(buddy)
                    .build());

        } catch(Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 버디 생성
     * @param buddy
     * @return
     * @throws Exception
     */
    @PostMapping
    public ResponseEntity saveBuddy(BuddyDTO buddy) throws Exception {
        BuddyUser savedBuddyUser = buddyService.save(buddy);
        return ResponseEntity.ok(ResponseMessage.builder()
                .status(StatusEnum.OK)
                .data(savedBuddyUser)
                .build());
    }

    /**
     * 버디 회원 생성
     * @param buddy
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/user")
    public ResponseEntity saveBuddyUser(BuddyDTO buddy) throws Exception {
        log.info("buddy = {}", buddy);
        BuddyUser savedBuddyUser = buddyService.saveBuddyUser(buddy);
        return ResponseEntity.ok(ResponseMessage.builder()
                .status(StatusEnum.OK)
                .data(savedBuddyUser)
                .build());
    }

    /**
     * 버디 수정 (이름 수정)
     * @param id
     * @param buddy
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity updateBuddy(@PathVariable("id") Long id, @RequestBody BuddyDTO buddy) throws Exception {
        Buddy updateBuddy = buddyService.update(id, buddy);
        return ResponseEntity.ok(ResponseMessage.builder()
                .status(StatusEnum.OK)
                .data(updateBuddy)
                .build());
    }

    /**
     * 회원 버디 수정 (알림설정 & 고정여부 & 초대수락여부)
     * @param id
     * @param buddy
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/user/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity updateBuddyUser(@PathVariable("id") Long id, @RequestBody BuddyDTO buddy) throws Exception {
        BuddyUser updateBuddyUser = buddyService.updateBuddyUser(id, buddy);
        return ResponseEntity.ok(ResponseMessage.builder()
                .status(StatusEnum.OK)
                .data(updateBuddyUser)
                .build());
    }

    /**
     * 버디 삭제
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity deleteBuddy(@PathVariable("id") Long id) throws Exception {
        Boolean deleteBuddy = buddyService.delete(id);
        return ResponseEntity.ok(ResponseMessage.builder()
                .status(StatusEnum.OK)
                .data(deleteBuddy)
                .build());

    }

    /**
     * 버디 회원 탈퇴
     * @param buddyId
     * @param userId
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/user")
    public ResponseEntity deleteBuddyUser(@RequestParam(value = "buddyId") Long buddyId, @RequestParam(value = "userId") Long userId) throws Exception {
        Boolean deleteBuddyUser = buddyService.deleteBuddyUser(buddyId, userId);
        return ResponseEntity.ok(ResponseMessage.builder()
                .status(StatusEnum.OK)
                .data(deleteBuddyUser)
                .build());
    }
}
