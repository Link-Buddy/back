package com.linkbuddy.global.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;


@Entity
@Data
@Table(name = "Link")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "link_url", nullable = false, length = 255)
    private String linkUrl;

    @Column(name = "link_group_id")
    private Long linkGroupId;

    @Column(name = "delete_tf", nullable = false)
    private Boolean deleteTf = false;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @Column(name = "user_id")
    private Long userId;
    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", linkGroupId=" + linkGroupId +
                ", deleteTf=" + deleteTf +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userId=" + userId +
                '}';
    }

    public boolean isHost(Long userId) {
        return this.userId.equals(userId);
    }
}