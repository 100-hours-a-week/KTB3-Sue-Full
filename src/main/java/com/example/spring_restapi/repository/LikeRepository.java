package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.Like;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
public class LikeRepository {
    private final Map<Long, Like> likeMap = new HashMap<>();

    public LikeRepository(){
        Set<Long> users1 = new HashSet<>();
        users1.add(1L);
        users1.add(2L);

        Set<Long> users2 = new HashSet<>();
        users1.add(2L);
        users1.add(3L);

        Like like1 = new Like(1L, users1);
        Like like2 = new Like(2L, users1);
        Like like3 = new Like(3L, users2);

        likeMap.put(like1.getPost_id(), like1);
        likeMap.put(like2.getPost_id(), like2);
        likeMap.put(like3.getPost_id(), like3);
    }

    public void save(Long post_id, Like like){
        likeMap.put(post_id, like);
    }

    public Integer getLikeCount(Long post_id){
        Like like = likeMap.get(post_id);
        return like.getLikedUserIds().size();
    }

    public void deleteLike(Long post_id) {
        likeMap.remove(post_id);
    }

//    public void addLikeUsers(Long post_id, Like like){
//        for(Long like_id : likeMap.keySet()){
//            if(like_id.equals(post_id)){
//                likeMap.
//            }
//        }
//    }
}
