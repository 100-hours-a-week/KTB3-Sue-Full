package com.example.spring_restapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "follow")
public class Follow {

    @Id
    @Column(name = "follower_id", nullable = false)
    private Long follower_id;

    @Id
    @Column(name = "following_id", nullable = false)
    private Long following_id;

    @Schema(description = "팔로우 생성일자", example = "20251020T10:00:00")
    @Setter
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Schema(description = "팔로우 수정일자", example = "20251020T10:00:00")
    @Setter
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Schema(description = "사용자 삭제일자", example = "20251020T10:00:00")
    @Setter
    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    protected Follow() {}

    public Follow(Long follower_id, Long following_id){
        if (follower_id == null) throw new IllegalArgumentException("follower's id is required");
        if (following_id == null) throw new IllegalArgumentException("following's id is required");
        this.follower_id = follower_id;
        this.following_id = following_id;
        this.createdAt = LocalDateTime.now();
    }

    public static Follow create(Long follower_id, Long following_id){
        return new Follow(follower_id, following_id);
    }
}
