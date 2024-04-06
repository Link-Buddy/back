package com.linkbuddy.global.entity;

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
import java.util.function.LongToIntFunction;

@Entity(name = "Buddy")
@Data
@Description(value = "버디")
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //아무런 값도 갖지 않는 의미 없는 객체 생성을 막음
public class Buddy {
    @Id // 테이블의 기본 키임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT를 사용합니다.
    @Comment(value = "버디ID")
    private Long id;

    @Column(nullable = false, length = 100)
    @Comment(value = "버디명")
    private String name;

    @Column(nullable = false)
    @Comment(value = "생성자ID")
    private Long creator_id;

    @CreationTimestamp  //Insert 쿼리 발생시 현재 시간 값 적용
    @Column(name = "created_at")
    @Comment(value = "생성일시")
    private Timestamp created_at;

    @UpdateTimestamp    //Update 쿼리 발생시 현재 시간 값 적용
    @Column(name = "updated_at")
    @Comment(value = "수정일시")
    private Timestamp updated_at;

    @Builder
    public Buddy(String name, Long creator_id) {
        this.name = name;
        this.creator_id = creator_id;
    }

    public void update(String name) {
        this.name = name;
    }
}
