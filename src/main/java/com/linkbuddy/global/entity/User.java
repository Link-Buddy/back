package com.linkbuddy.global.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Description;

import java.sql.Timestamp;

@Entity(name = "User")
@Data
@Description(value = "회원")
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //아무런 값도 갖지 않는 의미 없는 객체 생성을 막음
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment(value = "회원ID")
  private Long id;

  @Column(nullable = false, length = 255)
  @Comment(value = "이메일")
  private String email;

  @Column(length = 255)
  @Comment(value = "비밀번호")
  private String password;

  @Column(length = 255)
  @Comment(value = "이름")
  private String name;

  @Column(length = 255)
  @Comment(value = "소셜로그인")
  private String social;

  @Column(name = "status_cd")
  @Comment(value = "회원상태코드")
  private Integer statusCd;

  @Column(name = "file_id")
  @Comment(value = "프로필이미지파일ID")
  private Integer fileId;

  @CreationTimestamp  //Insert 쿼리 발생시 현재 시간 값 적용
  @Column(name = "created_at")
  @Comment(value = "생성일시")
  private Timestamp createdAt;

  @Column(name = "deleted_at")
  @Comment(value = "탈퇴일시")
  private Timestamp deletedAt;

  @CreationTimestamp
  @Column(name = "last_logged_at")
  @Comment(value = "탈퇴일시")
  private Timestamp lastLoggedAt;

  @Builder
  public User(String email, String name, String password, Integer statusCd, String social) {
    this.email = email;
    this.name = name;
    this.password = password;
    this.statusCd = statusCd;
    this.social = social;
  }


  public User update(String name) {
    this.name = name;

    return this;
  }

  public User updateLastLoggedAt(Timestamp lastLoggedAt) {
    this.lastLoggedAt = lastLoggedAt;
    return this;
  }
}