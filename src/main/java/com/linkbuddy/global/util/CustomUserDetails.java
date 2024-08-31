package com.linkbuddy.global.util;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * packageName    : com.linkbuddy.global.util
 * fileName       : CustomUserDetails
 * author         : yl951
 * date           : 2024-08-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-26        yl951       최초 생성
 */
public class CustomUserDetails implements UserDetails {

  private Long id;  // 사용자 ID
  private String username;  // 사용자 이름 또는 이메일
  private String password;  // 암호화된 비밀번호
  private Collection<? extends GrantedAuthority> authorities;  // 사용자 권한 목록

  public CustomUserDetails(Long id, String username) {
    this.id = id;
    this.username = username;
    this.authorities = Collections.emptyList();
  }

  public Long getId() {
    return id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
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