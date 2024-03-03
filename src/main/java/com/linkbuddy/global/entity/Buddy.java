package com.linkbuddy.global.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.function.LongToIntFunction;

@Entity(name = "Buddy")
@Data
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

}
