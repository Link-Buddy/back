package com.linkbuddy.domain.buddy;

import com.linkbuddy.domain.buddy.dto.BuddyDTO;
import com.linkbuddy.domain.buddy.repository.BuddyRepository;
import com.linkbuddy.domain.buddyUser.repository.BuddyUserRepository;
import com.linkbuddy.global.config.jwt.SecurityUtil;
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

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 회원 버디 리스트 조회
     * @return
     * @throws Exception
     */
    public List<BuddyDTO.BuddyResponse> findAll() throws Exception {
        try {
            Long currentUserId = securityUtil.getCurrentUserId();
            List<BuddyDTO.BuddyResponse> buddyList = buddyRepository.findBuddyByUserId(currentUserId);
            return buddyList;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 버디 상세 조회 (By buddyId)
     * @param id
     * @return
     * @throws Exception
     */
    public Optional<Buddy> findById(Long id) throws Exception {
        try {
            Optional<Buddy> buddy = buddyRepository.findById(id);
            log.info("buddy = {}", buddy);
            return buddy;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 버디 생성
     * @param buddyDTO
     * @return
     * @throws Exception
     */
    public BuddyUser create(BuddyDTO buddyDTO) throws Exception {
        try {
            Long currentUserId = securityUtil.getCurrentUserId();
            log.info("currentUserId = {}", currentUserId);

            Optional<Buddy> buddy = buddyRepository.findByNameAndCreatorId(buddyDTO.getName(), currentUserId);
            log.info("buddy = {}", buddy);
            if (!buddy.isEmpty()) {
                return null;
            } else {
                // 현재시간
                LocalDateTime now = LocalDateTime.now();

                Buddy newBuddy = Buddy.builder()
                        .name(buddyDTO.getName())
                        .creatorId(currentUserId)
                        .build();
                log.info("newBuddy = {}", newBuddy);
                // Buddy save
                Buddy savedBuddy = buddyRepository.save(newBuddy);

                BuddyUser newBuddyUser = BuddyUser.builder()
                        .userId(currentUserId)
                        .buddyId(savedBuddy.getId())
                        .senderId(null)
                        .alertTf(true)
                        .pinTf(false)
                        .acceptTf(true)
                        .acceptDt(Timestamp.valueOf(now))
                        .build();
                // Buddy user save
                return buddyUserRepository.save(newBuddyUser);
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 버디 수정 (이름 수정)
     * @param id
     * @param buddyDTO
     * @return
     * @throws Exception
     */
    @Transactional
    public Buddy update(Long id, BuddyDTO buddyDTO) throws Exception {
        try {
            Buddy buddy = buddyRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Not exist Buddy Data"));
            log.info("buddy.getCreator_id()= {}", buddyDTO.getUserId());
            log.info("buddy.getCreator_id() = {}", buddy.getCreatorId());
            // buddy를 생성한 사람만 이름 수정 가능
            if (Long.valueOf(buddyDTO.getUserId()) != buddy.getCreatorId()) {
                throw new IllegalArgumentException("Cannot access to update Buddy");
            }
            buddy.update(buddyDTO.getName());
            return buddy;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 버디 삭제
     * @param id
     * @return
     * @throws Exception
     */
    public Boolean delete(Long id) throws Exception {
        try {
            log.info("buddy id= {}", id);
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

}
