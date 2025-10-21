package com.example.spring_restapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Like {
    @Schema(description = "게시글 아이디", example = "2L")
    private Long post_id;

    @Schema(description = "좋아요를 누른 사용자들 아이디 세트", example = "[1L, 3L]")
    private final Set<Long> likedUserIds;

    public Like(Long post_id, Set<Long> likedUserIds) {
        this.post_id = post_id;
        if(likedUserIds == null) {
            this.likedUserIds = ConcurrentHashMap.newKeySet();
        } else {
            this.likedUserIds = ConcurrentHashMap.newKeySet();
            this.likedUserIds.addAll(likedUserIds);
        }
    }

    // Getter
    public Long getPost_id() { return post_id; }

    public Set<Long> getLikedUserIds() { return likedUserIds; }

    // Setter
    public void setPost_id(Long post_id) { this.post_id = post_id; }

    public Boolean isLikedByUser(Long user_id) { return likedUserIds.contains(user_id); }

    public void like(Long user_id) { likedUserIds.add(user_id); }

    public void unlike(Long user_id) { likedUserIds.remove(user_id); }
}
