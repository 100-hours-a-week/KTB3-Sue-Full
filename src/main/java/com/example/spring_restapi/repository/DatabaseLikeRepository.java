package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.Like;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class DatabaseLikeRepository implements LikeRepository {
    private final ConcurrentHashMap<Long, Like> likeMap = new ConcurrentHashMap<>();

    public DatabaseLikeRepository(){
        Set<Long> users1 = ConcurrentHashMap.newKeySet();
        users1.add(1L);
        users1.add(2L);

        Set<Long> users2 = ConcurrentHashMap.newKeySet();
        users2.add(2L);
        users2.add(3L);

        Like like1 = new Like(1L, users1);
        Like like2 = new Like(2L, users1);
        Like like3 = new Like(3L, users2);

        save(like1.getPost_id(), like1);
        save(like2.getPost_id(), like2);
        save(like3.getPost_id(), like3);
    }

    @Override
    public Like save(Long post_id, Like like){
        return likeMap.put(post_id, like);
    }

    @Override
    public Optional<Like> findLikeByPostId(Long post_id){
        return Optional.ofNullable(likeMap.get(post_id));
    }

    @Override
    public Optional<Like> updateLikeByPostId(Long post_id, Like like){
        return Optional.ofNullable(likeMap.put(post_id, like));
    }

    @Override
    public Optional<Like> deleteLikePostInfo(Long post_id) {
        return Optional.ofNullable(likeMap.remove(post_id));
    }

}
