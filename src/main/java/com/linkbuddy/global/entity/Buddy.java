package com.linkbuddy.global.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity(name = "Buddy")
@Data
public class Buddy {
    @Id // 테이블의 기본 키임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT를 사용합니다.
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    private Timestamp created_at;

    private Timestamp updated_at;
}
