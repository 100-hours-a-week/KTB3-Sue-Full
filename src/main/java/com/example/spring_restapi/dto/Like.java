package com.example.spring_restapi.dto;

import java.util.HashSet;
import java.util.Set;

public class Like {
    private Long post_id;
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
