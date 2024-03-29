package com.linkbuddy.domain.buddy;

import com.linkbuddy.domain.buddy.dto.BuddyDTO;
import com.linkbuddy.domain.buddy.repository.BuddyRepository;
import com.linkbuddy.domain.buddy.repository.BuddyUserRepository;
import com.linkbuddy.global.entity.Buddy;
import com.linkbuddy.global.entity.BuddyUser;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BuddyService {
    @Autowired
    private BuddyRepository buddyRepository;

    @Autowired
    private BuddyUserRepository buddyUserRepository;

    public List<BuddyDTO> findAll(Long userId) {
        List<BuddyDTO> buddies = buddyUserRepository.findBuddyById(Math.toIntExact(userId));
        return buddies;
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
            Buddy newBuddy = Buddy.builder()
                    .name(buddy.getName())
                    .creator_id(Long.valueOf(buddy.getUserId()))
                    .build();
            // Buddy save
            Buddy savedBuddy = buddyRepository.save(newBuddy);

            BuddyUser newBuddyUser = BuddyUser.builder()
                    .userId(Long.valueOf(buddy.getUserId()))
                    .buddyId(savedBuddy.getId())
                    .alertTf(buddy.getAlertTf())
                    .pinTf(buddy.getPinTf())
                    .build();
            // Buddy user save
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
}
