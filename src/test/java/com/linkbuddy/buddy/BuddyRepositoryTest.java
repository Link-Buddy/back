package com.linkbuddy.buddy;

import com.linkbuddy.domain.buddy.repository.BuddyRepository;
import com.linkbuddy.global.config.AppConfig;
import com.linkbuddy.global.entity.Buddy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = AppConfig.class)
public class BuddyRepositoryTest {

    @Autowired
    private BuddyRepository buddyRepository;

    @Test
    public void testSaveBuddy() {
        // Buddy 엔티티 생성
        buddy.setName("John Doe");
        // Buddy 저장
        Buddy savedBuddy = buddyRepository.save(buddy);

        // 검증
        assertThat(savedBuddy).isNotNull();
        assertThat(savedBuddy.getId()).isNotNull();
        assertThat(savedBuddy.getName()).isEqualTo("John Doe");
    }
}
