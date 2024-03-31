package com.linkbuddy.global.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity(name = "BuddyUser")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //아무런 값도 갖지 않는 의미 없는 객체 생성을 막음
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
    @JsonIgnore
    private Buddy buddy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private User user;


    @Builder
    public BuddyUser(Long userId, Long buddyId, Boolean alertTf, Boolean pinTf) {
        this.userId = userId;
        this.buddyId = buddyId;
        this.alertTf = alertTf;
        this.pinTf = pinTf;
    }

    public void update(Boolean alertTf, Boolean pinTf) {
        this.alertTf = alertTf;
        this.pinTf = pinTf;
    }
}
