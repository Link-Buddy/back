package com.linkbuddy.global.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linkbuddy.domain.category.CategoryDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

/**
 * packageName    : com.linkbuddy.global.entity
 * fileName       : Category
 * author         : yl951
 * date           : 2024-04-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-14        yl951       최초 생성
 */

@Entity
@Data
@Table(name = "Category")
@NoArgsConstructor
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "category_name", nullable = false, length = 50)
  private String categoryName;

  @Column(name = "file_id", nullable = true)
  private Long fileId;

  @Column(name = "share_type_cd", nullable = false)
  @Comment(value = "개인 or 공유")
  private Long shareTypeCd;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "buddy_id", nullable = true)
  private Long buddyId;

  @Column(name = "delete_tf")
  private Boolean deleteTf = false;

  @CreationTimestamp  //Insert 쿼리 발생시 현재 시간 값 적용
  @Column(name = "created_at")
  private Timestamp createdAt;

  @UpdateTimestamp    //Update 쿼리 발생시 현재 시간 값 적용
  @Column(name = "updated_at")
  private Timestamp updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "buddy_id", referencedColumnName = "id", insertable = false, updatable = false)
  @JsonIgnore
  private Buddy buddy;

  @Builder
  public Category(String categoryName, Long shareTypeCd, Long userId, Long buddyId) {
    this.categoryName = categoryName;
    this.shareTypeCd = shareTypeCd;
    this.userId = userId;
    this.buddyId = buddyId;
  }

  public void updateCategory(CategoryDto.Update updateDto) {
    this.categoryName = updateDto.getCategoryName();
    this.fileId = updateDto.getFileId();
  }
}
