package com.linkbuddy.domain.buddy;

import com.linkbuddy.domain.buddy.dto.BuddyDTO;
import com.linkbuddy.domain.buddy.dto.BuddyInterface;
import com.linkbuddy.domain.buddy.repository.BuddyRepository;
import com.linkbuddy.domain.buddy.repository.BuddyUserRepository;
import com.linkbuddy.domain.user.dto.UserInterface;
import com.linkbuddy.global.entity.Buddy;
import com.linkbuddy.global.entity.BuddyUser;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BuddyService {
    @Autowired
    private BuddyRepository buddyRepository;

    @Autowired
    private BuddyUserRepository buddyUserRepository;

    public List<BuddyInterface> findAll(Long userId) {
        List<BuddyInterface> buddyList = buddyUserRepository.findBuddyById(userId);
        return buddyList;
    }

    public List<UserInterface> findUserAll(Long buddyId) {
        List<UserInterface> userList = buddyUserRepository.findUserByBuddyId(buddyId);
        return userList;
    }

    public Optional<Buddy> findById(Long id) {
        Optional<Buddy> buddy = buddyRepository.findById(id);
        log.info("buddy = {}", buddy);
        return buddy;
    }


    /**
     * @param buddy
     * @return
     * @throws Exception
     */
    public BuddyUser save(BuddyDTO buddy) throws Exception {
        try {
            // 현재시간
            LocalDateTime now = LocalDateTime.now();

            Buddy newBuddy = Buddy.builder()
                    .name(buddy.getName())
                    .creator_id(buddy.getUserId())
                    .build();
            // Buddy save
            Buddy savedBuddy = buddyRepository.save(newBuddy);

            BuddyUser newBuddyUser = BuddyUser.builder()
                    .userId(buddy.getUserId())
                    .buddyId(savedBuddy.getId())
                    .alertTf(buddy.getAlertTf())
                    .pinTf(buddy.getPinTf())
                    .acceptTf(true)
                    .acceptDt(Timestamp.valueOf(now))
                    .build();
            // Buddy user save
            return buddyUserRepository.save(newBuddyUser);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public BuddyUser saveBuddyUser(BuddyDTO buddy) throws Exception {
        try {
            BuddyUser newBuddyUser = BuddyUser.builder()
                    .userId(buddy.getUserId())
                    .buddyId(buddy.getBuddyId())
                    .alertTf(true)
                    .pinTf(false)
                    .acceptTf(false)
                    .build();
            return buddyUserRepository.save(newBuddyUser);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional
    public Buddy update(Long id, BuddyDTO buddyDTO) throws Exception {
        try {
            Buddy buddy = buddyRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Not exist Buddy Data"));
            log.info("buddy.getCreator_id()= {}", buddyDTO.getUserId());
            log.info("buddy.getCreator_id() = {}", buddy.getCreator_id());
            // buddy를 생성한 사람만 이름 수정 가능
            if (Long.valueOf(buddyDTO.getUserId()) != buddy.getCreator_id()) {
                throw new IllegalArgumentException("Cannot access to update Buddy");
            }
            buddy.update(buddyDTO.getName());
            return buddy;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional
    public BuddyUser updateBuddyUser(Long id, BuddyDTO buddyDTO) throws Exception {
        try {
            // 현재시간
            LocalDateTime now = LocalDateTime.now();

            // 버디 회원 조회
            BuddyUser buddyUser = buddyUserRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Not exist Buddy user Data"));
            log.info("buddyUser = {}", buddyUser);

            BuddyUser newBuddyUser = BuddyUser.builder()
                    .userId(buddyUser.getUserId())
                    .buddyId(buddyUser.getBuddyId())
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
    public Boolean delete(Long id) throws Exception {
        try {
            log.info("buddy user id= {}", id);
            // buddyUser list 조회
            List<BuddyUser> buddyUserList =  buddyUserRepository.findBuddyUsersByBuddyId(id);
            if (buddyUserList.size() > 0) {
                buddyUserList.forEach(buddyDTO -> {
                    buddyUserRepository.deleteById(buddyDTO.getId());
                });
                // buddy 삭제
                buddyRepository.deleteById(id);
                return true;
            } else {
                throw new IllegalArgumentException("Not exist Buddy Data");
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

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
