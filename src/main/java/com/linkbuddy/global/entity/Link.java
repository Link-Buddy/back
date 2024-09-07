package com.linkbuddy.global.entity;

import com.linkbuddy.domain.link.LinkDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;


@Entity
@Data
@Table(name = "Link")
@NoArgsConstructor
public class Link {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "link_url", nullable = false, length = 1024)
  private String linkUrl;

  @Column(name = "category_id")
  private Long categoryId;

  @Column(name = "delete_tf")
  private Boolean deleteTf = false;

  @CreationTimestamp  //Insert 쿼리 발생시 현재 시간 값 적용
  @Column(name = "created_at")
  private Timestamp createdAt;

  @UpdateTimestamp    //Update 쿼리 발생시 현재 시간 값 적용
  @Column(name = "updated_at")
  private Timestamp updatedAt;

  @Column(name = "user_id", nullable = true)
  private Long userId;


  @Builder
  public Link(String name, String description, String linkUrl, Long categoryId, Long userId) {
    this.name = name;
    this.description = description;
    this.linkUrl = linkUrl;
    this.categoryId = categoryId;
    this.userId = userId;
  }

  public boolean isHost(Long userId) {
    return this.userId.equals(userId);
  }

  public void updateLink(LinkDto.Update updateLinkDto) {
    this.name = updateLinkDto.getName();
    this.description = updateLinkDto.getDescription();
    this.categoryId = updateLinkDto.getCategoryId();
    this.deleteTf = updateLinkDto.getDeleteTf();
  }

}