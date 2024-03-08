package com.linkbuddy.domain.buddy;

import com.linkbuddy.domain.buddy.dto.BuddyDTO;
import com.linkbuddy.global.entity.Buddy;
import com.linkbuddy.global.entity.BuddyUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("buddy")
public class BuddyController {
    @Autowired
    BuddyService buddyService;

    @GetMapping
    public ResponseEntity<List<Buddy>> getBuddyList() {
        List<Buddy> buddies = buddyService.findAll();
        return new ResponseEntity<List<Buddy>>(buddies, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Buddy> getBuddy(@PathVariable("id")Long id) {
        Optional<Buddy> buddy = buddyService.findById(id);
        log.info("info log = {}", id);
        return new ResponseEntity<Buddy>(buddy.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BuddyUser> saveBuddy(BuddyDTO buddy) throws Exception {
        BuddyUser savedBuddyUser = buddyService.save(buddy);
        return new ResponseEntity<BuddyUser>(savedBuddyUser, HttpStatus.OK);
    }
}
