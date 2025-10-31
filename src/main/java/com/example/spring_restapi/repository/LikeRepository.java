package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.Like;

import java.util.Optional;

public interface LikeRepository {

    Like save(Long post_id, Like like);

    Optional<Like> findLikeByPostId(Long post_id);

    Optional<Like> updateLikeByPostId(Long post_id, Like like);

    Optional<Like> deleteLikePostInfo(Long post_id);
}
