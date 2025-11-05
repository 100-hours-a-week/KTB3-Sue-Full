package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.UserIdBodyRequest;
import com.example.spring_restapi.dto.response.LikeListResponse;
import com.example.spring_restapi.dto.response.LikeResponse;
import com.example.spring_restapi.model.Like;


public interface LikeService {

    void validate(Long post_id, UserIdBodyRequest req);

    LikeResponse like(Long post_id, UserIdBodyRequest req);

    Integer getLikeCount(Long post_id);

    LikeResponse unlike(Long post_id, UserIdBodyRequest req);

    LikeListResponse getLikes(Long post_id);
}
