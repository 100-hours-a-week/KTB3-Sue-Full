package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.UserIdBodyRequest;
import com.example.spring_restapi.model.Like;

public interface LikeService {

    Like validate(Long post_id, UserIdBodyRequest req);

    Like like(Long post_id, UserIdBodyRequest req);

    Integer getLikeCount(Long post_id);

    Like unlike(Long post_id, UserIdBodyRequest req);
}
