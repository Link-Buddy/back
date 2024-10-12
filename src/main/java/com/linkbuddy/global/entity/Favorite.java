package com.linkbuddy.global.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * packageName    : com.linkbuddy.global.entity
 * fileName       : Favorite
 * author         : yl951
 * date           : 2024-10-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-10-12        yl951       최초 생성
 */
@Entity
@Data
@Table(name = "Favorite")
@NoArgsConstructor
public class Favorite {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "link_id", nullable = false)
  private Long linkId;

  @CreationTimestamp
  @Column(name = "created_at")
  private Timestamp createdAt;

  @Builder
  public Favorite(Long userId, Long linkId) {
    this.userId = userId;
    this.linkId = linkId;
  }
}
