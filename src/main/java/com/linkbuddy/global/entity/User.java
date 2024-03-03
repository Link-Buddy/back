package com.linkbuddy.global.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.function.LongToIntFunction;

@Entity(name = "User")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(length = 255)
    private String name;

    @Column(length = 255)
    private String social;

    @Column(name = "status_cd")
    private Integer statusCd;

    @Column(name = "file_id")
    private Integer fileId;

    @CreationTimestamp  //Insert 쿼리 발생시 현재 시간 값 적용
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;
}