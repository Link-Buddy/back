package com.linkbuddy.domain.buddy;

import com.linkbuddy.domain.buddy.dto.BuddyDTO;
import com.linkbuddy.domain.buddy.repository.BuddyRepository;
import com.linkbuddy.domain.buddy.repository.BuddyUserRepository;
import com.linkbuddy.global.entity.Buddy;
import com.linkbuddy.global.entity.BuddyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BuddyService {
    @Autowired
    private BuddyRepository buddyRepository;

    @Autowired
    private BuddyUserRepository buddyUserRepository;

    public List<Buddy> findAll() {
        List<Buddy> buddies = new ArrayList<>();
        buddyRepository.findAll().forEach(e -> buddies.add(e));
        return buddies;
    }

    public Optional<Buddy> findById(Long id) {
        Optional<Buddy> buddy = buddyRepository.findById(id);
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
                    .build();
            // Buddy save
            Buddy savedBuddy = buddyRepository.save(newBuddy);

            BuddyUser newBuddyUser = BuddyUser.builder()
                    .userId(buddy.getUserId())
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
}
