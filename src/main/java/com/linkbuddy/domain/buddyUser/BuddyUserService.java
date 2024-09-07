package com.linkbuddy.domain.buddyUser;

import com.linkbuddy.domain.buddy.dto.BuddyDTO;
import com.linkbuddy.domain.buddyUser.repository.BuddyUserRepository;
import com.linkbuddy.domain.user.dto.UserDTO;
import com.linkbuddy.domain.user.repository.UserRepository;
import com.linkbuddy.global.config.jwt.SecurityUtil;
import com.linkbuddy.global.entity.BuddyUser;
import com.linkbuddy.global.entity.User;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName    : com.linkbuddy.domain.buddyUser
 * fileName       : BuddyUserService
 * author         : admin
 * date           : 2024-04-14
 * description    : 버디 회원 Service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-14        admin       최초 생성
 */
@Service
@Slf4j
public class BuddyUserService {
    @Autowired
    private BuddyUserRepository buddyUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 버디 참여 회원 리스트 조회
     * @param buddyId
     * @return
     * @throws Exception
     */
    public List<UserDTO.UserResponse> findUserAll(Long buddyId) throws Exception {
        try {
            List<UserDTO.UserResponse> userList = buddyUserRepository.findUserByBuddyId(buddyId);
            return userList;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 버디 회원 생성 (친구초대)
     * @param buddy
     * @return
     * @throws Exception
     */
    public BuddyUser createBuddyUser(BuddyDTO buddy) throws Exception {
        try {
            Long currentUserId = securityUtil.getCurrentUserId();
            User invitedUser = userRepository.customFindByEmail(buddy.getEmail());

            // 버디 회원 조회
            BuddyUser buddyUser = buddyUserRepository.findBuddyUserByBuddyIdAndUserId(buddy.getBuddyId(), invitedUser.getId());
            if (buddyUser == null) {
                BuddyUser newBuddyUser = BuddyUser.builder()
                        .userId(invitedUser.getId())
                        .buddyId(buddy.getBuddyId())
                        .senderId(currentUserId)
                        .alertTf(true)  //알림여부
                        .pinTf(false)   //고정여부
                        .acceptTf(false)    //수락여부
                        .build();
                return buddyUserRepository.save(newBuddyUser);
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 회원 버디 초대 리스트 조회
     * @param userId
     * @return
     * @throws Exception
     */
    public List<BuddyDTO.BuddyInvitationResponse> findBuddyUserInvitationAll(Long userId) throws Exception {
        try {
            List<BuddyDTO.BuddyInvitationResponse> invitationList = buddyUserRepository.findBuddyUserInvitationsByUserId(userId);
            return invitationList;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 회원 버디 수정
     * @param id
     * @param buddyDTO
     * @return
     * @throws Exception
     */
    @Transactional
    public BuddyUser updateBuddyUser(Long id, BuddyDTO buddyDTO) throws Exception {
        try {
            // 현재시간
            LocalDateTime now = LocalDateTime.now();

            // 버디 회원 조회
            BuddyUser buddyUser = buddyUserRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Not exist Buddy user Data"));
            log.info("buddyUser = {}", buddyUser);

            Long userId = securityUtil.getCurrentUserId();

            BuddyUser newBuddyUser = BuddyUser.builder()
                    .userId(userId)
                    .buddyId(buddyUser.getBuddyId())
                    .senderId(buddyUser.getSenderId())
                    .alertTf(buddyDTO.getAlertTf() != null ? buddyDTO.getAlertTf() : buddyUser.getAlertTf())
                    .pinTf(buddyDTO.getPinTf() != null ? buddyDTO.getPinTf() : buddyUser.getPinTf())
                    .acceptTf(buddyDTO.getAcceptTf() != null ? buddyDTO.getAcceptTf() : buddyUser.getAcceptTf())
                    .acceptDt(buddyDTO.getAcceptTf() != null ? Timestamp.valueOf(now) : buddyUser.getAcceptDt())
                    .build();

            buddyUser.update(newBuddyUser);
            log.info("buddyUser after update = {}", buddyUser);
            return buddyUser;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 회원 버디 탈퇴
     * @param buddyId
     * @param userId
     * @return
     * @throws Exception
     */
    public Boolean deleteBuddyUser(Long buddyId, Long userId) throws Exception {
        try {
            log.info("buddy user id= {}", buddyId);
            log.info("user id= {}", userId);

            BuddyUser buddyUser = buddyUserRepository.findBuddyUserByBuddyIdAndUserId(buddyId, userId);

            if (buddyUser != null) {
                buddyUserRepository.deleteById(buddyUser.getId());
                return true;
            } else {
                throw new IllegalArgumentException("Not exist Buddy User Data");
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
