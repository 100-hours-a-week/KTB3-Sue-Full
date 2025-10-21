package com.example.spring_restapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashSet;
import java.util.Set;

public class Like {
    @Schema(description = "게시글 아이디", example = "2L")
    private Long post_id;

    @Schema(description = "좋아요를 누른 사용자들 아이디 세트", example = "[1L, 3L]")
    private Set<Long> likedUserIds = new HashSet<>();

    public Like(){}

    public Like(Long post_id, Set<Long> likedUserIds){
        this.post_id = post_id;
        this.likedUserIds = likedUserIds;
    }

    public Long getPost_id() { return post_id; }

    public Set<Long> getLikedUserIds() { return likedUserIds; }

    public void setLikedUserIds(Set<Long> likedUserIds) {
        this.likedUserIds = likedUserIds;
    }
}
