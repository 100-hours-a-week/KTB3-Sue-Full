package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.UserIdBodyRequest;
import com.example.spring_restapi.dto.response.LikeListResponse;
import com.example.spring_restapi.dto.response.LikeResponse;
import com.example.spring_restapi.model.Like;

import com.example.spring_restapi.model.Post;
import com.example.spring_restapi.model.User;
import com.example.spring_restapi.repository.LikeRepository;
import com.example.spring_restapi.repository.PostRepository;
import com.example.spring_restapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService{
    private final LikeRepository databaseLikeRepository;
    private final UserRepository databaseUserRepository;
    private final PostRepository databasePostRepository;

    public LikeServiceImpl(LikeRepository databaseLikeRepository, UserRepository databaseUserRepository, PostRepository databasePostRepository){
        this.databaseLikeRepository = databaseLikeRepository;
        this.databaseUserRepository = databaseUserRepository;
        this.databasePostRepository = databasePostRepository;
    }

    @Override
    @Transactional
    public void validate(Long post_id, UserIdBodyRequest req){

        if(databaseUserRepository.findUserById(req.getUser_id()).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        if(databasePostRepository.findPostByPostId(post_id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

    }

    @Override
    @Transactional
    public LikeResponse like(Long post_id, UserIdBodyRequest req){
        validate(post_id, req);

        if(databaseLikeRepository.findLikeOfPostByUserId(post_id, req.getUser_id()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "already_likes");
        }

        Optional<Post> findPost = databasePostRepository.findPostByPostId(post_id);
        if(findPost.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Optional<User> findUser = databaseUserRepository.findUserById(req.getUser_id());
        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Post likePost = findPost.get();
        User likeUser = findUser.get();

        Like like = Like.create(likePost, likeUser);

        databaseLikeRepository.save(like);
        databasePostRepository.likeBySomeone(post_id);

        return new LikeResponse(like.getId(), post_id, req.getUser_id());
    }

    @Override
    @Transactional
    public Integer getLikeCount(Long post_id){
        Optional<Post> findPost = databasePostRepository.findPostByPostId(post_id);

        if(findPost.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Post post = findPost.get();

        return post.getLikeCount();
    }

    @Override
    @Transactional
    public LikeResponse unlike(Long post_id, UserIdBodyRequest req){
        validate(post_id, req);

        Optional<Like> data = databaseLikeRepository.findLikeOfPostByUserId(post_id, req.getUser_id());

        if(data.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Like unlike = data.get();

        databaseLikeRepository.deleteLike(unlike.getId());
        databasePostRepository.unlikeBySomeone(post_id);

        return new LikeResponse(unlike.getId(), post_id, req.getUser_id());
    }

    @Override
    @Transactional
    public LikeListResponse getLikes(Long post_id){

        if(databasePostRepository.findPostByPostId(post_id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        List<Like> likes = databaseLikeRepository.findLikesByPostId(post_id);

        List<LikeResponse> data = likes.stream().map(like -> new LikeResponse(like.getId(), post_id, like.getUser().getId())).toList();

        return new LikeListResponse(data);
    }
}
