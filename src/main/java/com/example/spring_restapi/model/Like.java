package com.example.spring_restapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Like {

    @Schema(description = "좋아요 아이디", example = "3L")
    private Long id;

    @Schema(description = "게시글 아이디", example = "2L")
    private Long post_id;

    @Schema(description = "게시글에 좋아요를 누른 유저 아이디", example = "1L")
    private Long liked_user_id;

//    @Schema(description = "좋아요를 누른 사용자들 아이디 세트", example = "[1L, 3L]")
//    private final Set<Long> likedUserIds;

//    public Like(Long post_id, Set<Long> likedUserIds) {
//        this.post_id = post_id;
//        if(likedUserIds == null) {
//            this.likedUserIds = ConcurrentHashMap.newKeySet();
//        } else {
//            this.likedUserIds = ConcurrentHashMap.newKeySet();
//            this.likedUserIds.addAll(likedUserIds);
//        }
//    }

    protected Like() {}

    public Like(Long post_id, Long liked_user_id){
        this.post_id = post_id;
        this.liked_user_id = liked_user_id;
    }

}
