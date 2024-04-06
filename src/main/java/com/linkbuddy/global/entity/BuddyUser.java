package com.linkbuddy.global.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linkbuddy.domain.buddy.dto.BuddyDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Description;

import java.sql.Timestamp;

@Entity(name = "BuddyUser")
@Data
@Description(value = "버디회원")
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //아무런 값도 갖지 않는 의미 없는 객체 생성을 막음
public class BuddyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "버디회원ID")
    private Long id;

    @Column(name = "buddy_id", nullable = false)
    @Comment(value = "버디ID")
    private Long buddyId;

    @Column(name = "user_id", nullable = false)
    @Comment(value = "회원ID")
    private Long userId;

    @Column(name = "alert_tf", nullable = false)
    @Comment(value = "알림여부")
    private Boolean alertTf = true;

    @Column(name = "pin_tf", nullable = false)
    @Comment(value = "고정여부")
    private Boolean pinTf = false;

    @Column(name = "accept_tf", nullable = false)
    @Comment(value = "초대수락여부")
    private Boolean acceptTf = false;

    @Column(name = "accept_dt")
    @Comment(value = "초대수락일시")
    private Timestamp acceptDt;

    @CreationTimestamp  //Insert 쿼리 발생시 현재 시간 값 적용
    @Column(name = "created_at")
    @Comment(value = "생성일시")
    private Timestamp created_at;

    @UpdateTimestamp    //Update 쿼리 발생시 현재 시간 값 적용
    @Column(name = "updated_at")
    @Comment(value = "수정일시")
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
    public BuddyUser(Long userId, Long buddyId, Boolean alertTf, Boolean pinTf, Boolean acceptTf, Timestamp acceptDt) {
        this.userId = userId;
        this.buddyId = buddyId;
        this.alertTf = alertTf;
        this.pinTf = pinTf;
        this.acceptTf = acceptTf;
        this.acceptDt = acceptDt;
    }

    public void update(Boolean alertTf, Boolean pinTf, Boolean acceptTf, Timestamp acceptDt) {
        this.alertTf = alertTf;
        this.pinTf = pinTf;
        this.acceptTf = acceptTf;
        this.acceptDt = acceptDt;
    }

    public void update(BuddyUser buddyuser) {
        this.alertTf = buddyuser.getAlertTf();
        this.pinTf = buddyuser.getPinTf();
        this.acceptTf = buddyuser.getAcceptTf();
        this.acceptDt = buddyuser.getAcceptDt();
    }

}
