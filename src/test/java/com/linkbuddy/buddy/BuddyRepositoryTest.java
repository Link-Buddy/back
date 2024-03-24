package com.linkbuddy.buddy;

import com.linkbuddy.domain.buddy.repository.BuddyRepository;
import com.linkbuddy.global.entity.Buddy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BuddyRepositoryTest {
    @Autowired
    private BuddyRepository buddyRepository;
    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnection() {
        try {
            Connection con = dataSource.getConnection();
            System.out.println("connnnnnnn" + con.getMetaData());
            System.out.println("driver" + con.getMetaData().getDriverName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveBuddy() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        // Buddy 엔티티 생성
        Buddy buddy = Buddy.builder().name("yuji").build();
        // Buddy 저장
        System.out.println(buddy);
        Buddy savedBuddy = buddyRepository.save(buddy);
        assertThat(savedBuddy.getName()).isEqualTo("yuji");

        // 검증
//        assertThat(savedBuddy).isNotNull();
//        assertThat(savedBuddy.getId()).isNotNull();
    }

    @Test
    public void testGetBuddy() {
        List<Buddy> buddyList = buddyRepository.findAll();
        System.out.println(buddyList);
    }
}
