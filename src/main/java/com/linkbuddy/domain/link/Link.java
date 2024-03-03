package com.linkbuddy.domain.link;

import jakarta.persistence.*;

import java.sql.Timestamp;


@Entity
@Table(name = "Link")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "link_url", nullable = false, length = 255)
    private String linkUrl;

    @Column(name = "link_group_id")
    private Integer linkGroupId;

    @Column(name = "delete_tf", nullable = false)
    private Boolean deleteTf = false;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @Column(name = "user_id")
    private Integer userId;

}