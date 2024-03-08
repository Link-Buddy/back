package com.linkbuddy.global.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.function.LongToIntFunction;

@Entity(name = "Buddy")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //아무런 값도 갖지 않는 의미 없는 객체 생성을 막음
public class Buddy {
    @Id // 테이블의 기본 키임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT를 사용합니다.
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @CreationTimestamp  //Insert 쿼리 발생시 현재 시간 값 적용
    @Column(name = "created_at")
    private Timestamp created_at;

    @UpdateTimestamp    //Update 쿼리 발생시 현재 시간 값 적용
    @Column(name = "updated_at")
    private Timestamp updated_at;

    @Builder
    public Buddy(String name) {
        this.name = name;
    }
}
