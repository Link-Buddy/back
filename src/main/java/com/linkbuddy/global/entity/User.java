package com.linkbuddy.global.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Description;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Entity(name = "User")
@Data
@Description(value = "회원")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //아무런 값도 갖지 않는 의미 없는 객체 생성을 막음
public class User implements UserDetails {
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

  @Builder
  public User(String email, String name, String password, Integer statusCd) {
    this.email = email;
    this.name = name;
    this.password = password;
    this.statusCd = statusCd;
  }

  @ElementCollection(fetch = FetchType.EAGER)
  @Builder.Default
  private List<String> roles = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
  }


  @Override
  public String getUsername() {
    return this.name;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}