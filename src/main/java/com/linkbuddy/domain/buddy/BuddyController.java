package com.linkbuddy.domain.buddy;

import com.linkbuddy.domain.buddy.dto.BuddyDTO;
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
    public ResponseEntity<List<BuddyDTO>> getBuddyList(@RequestParam(value = "userId") Long userId) {
        log.debug("userId = {}", userId);
        List<BuddyDTO> buddies = buddyService.findAll(userId);
        return new ResponseEntity<List<BuddyDTO>>(buddies, HttpStatus.OK);
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
    public ResponseEntity<BuddyUser> saveBuddy(BuddyDTO buddy) throws Exception {
        BuddyUser savedBuddyUser = buddyService.save(buddy);
        return new ResponseEntity<BuddyUser>(savedBuddyUser, HttpStatus.OK);
    }

    /**
     * 버디 수정
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
}
