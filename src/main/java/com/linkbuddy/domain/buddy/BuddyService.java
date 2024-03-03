package com.linkbuddy.domain.buddy;

import com.linkbuddy.domain.buddy.repository.BuddyRepository;
import com.linkbuddy.global.entity.Buddy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BuddyService {
    @Autowired
    private BuddyRepository buddyRepository;

    public List<Buddy> findAll() {
        List<Buddy> buddies = new ArrayList<>();
        buddyRepository.findAll().forEach(e -> buddies.add(e));
        return buddies;
    }

    public Optional<Buddy> findById(Long id) {
        Optional<Buddy> buddy = buddyRepository.findById(id);
        return buddy;
    }

    public Buddy save(Buddy buddy) {
        buddyRepository.save(buddy);
        return buddy;
    }
}
