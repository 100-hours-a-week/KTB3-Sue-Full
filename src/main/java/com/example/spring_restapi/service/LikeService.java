package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.UserIdBodyRequest;
import com.example.spring_restapi.model.Like;
import com.example.spring_restapi.repository.LikeRepository;
import com.example.spring_restapi.repository.PostRepository;
import com.example.spring_restapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public LikeService(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository){
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Like like(Long post_id, UserIdBodyRequest req){
        if(userRepository.findUserById(req.getUser_id()).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }
        if(postRepository.findPostByPostId(post_id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Optional<Like> existing = likeRepository.findLikeByPostId(post_id);
        if(existing.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Like like = existing.get();

        if(like.isLikedByUser(req.getUser_id())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "already_like");
        }

        like.like(req.getUser_id());

        return like;
    }

    public Integer getLikeCount(Long post_id){
        Optional<Like> findLike = likeRepository.findLikeByPostId(post_id);
        if(findLike.isEmpty()) return null;

        return findLike.get().getLikedUserIds().size();
    }

    public Like unlike(Long post_id, UserIdBodyRequest req){
        if(userRepository.findUserById(req.getUser_id()).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }
        if(postRepository.findPostByPostId(post_id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }
       Optional<Like> existing = likeRepository.findLikeByPostId(post_id);
        if(existing.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Like like = existing.get();

        if(!like.isLikedByUser(req.getUser_id())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        like.unlike(req.getUser_id());

        return like;
    }
}
