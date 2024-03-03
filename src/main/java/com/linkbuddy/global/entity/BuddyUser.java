package com.linkbuddy.global.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity(name = "BuddyUser")
@Data
public class BuddyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "buddy_id", nullable = false)
    private Long buddyId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "alert_tf", nullable = false)
    private Boolean alertTf = true;

    @Column(name = "pin_tf", nullable = false)
    private Boolean pinTf = false;

    @CreationTimestamp  //Insert 쿼리 발생시 현재 시간 값 적용
    @Column(name = "created_at")
    private Timestamp created_at;

    @UpdateTimestamp    //Update 쿼리 발생시 현재 시간 값 적용
    @Column(name = "updated_at")
    private Timestamp updated_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buddy_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Buddy buddy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
}
