package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.Like;

import java.util.List;
import java.util.Optional;

public interface LikeRepository {

    void save(Like like);

    List<Like> findLikesByPostId(Long post_id);

    List<Like> findLikesByUserId(Long user_id);

    Optional<Like> findLikeOfPostByUserId(Long post_id, Long user_id);

    void deleteLike(Long id);

    void deleteLikeOfPostByUserId(Long post_id, Long user_id);

    List<Like> deleteLikePostInfo(Long post_id);
}
