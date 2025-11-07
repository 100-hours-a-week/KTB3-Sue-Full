package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.UserIdBodyRequest;
import com.example.spring_restapi.dto.response.LikeListResponse;
import com.example.spring_restapi.dto.response.LikeResponse;
import com.example.spring_restapi.model.Like;

import com.example.spring_restapi.model.Post;
import com.example.spring_restapi.model.User;
import com.example.spring_restapi.model.UserProfile;
import com.example.spring_restapi.repository.LikeRepository;
import com.example.spring_restapi.repository.PostRepository;
import com.example.spring_restapi.repository.UserProfileRepository;
import com.example.spring_restapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeServiceImpl implements LikeService{
    private final LikeRepository databaseLikeRepository;
    private final UserRepository databaseUserRepository;
    private final UserProfileRepository databaseUserProfileRepository;
    private final PostRepository databasePostRepository;

    @Override
    public void validate(Long post_id, UserIdBodyRequest req){

        if(databaseUserRepository.findUserById(req.getUser_id()).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        if(databasePostRepository.findPostById(post_id).isEmpty()){
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

        Optional<Post> findPost = databasePostRepository.findPostById(post_id);
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
        databasePostRepository.increaseLikeCount(post_id);

        Optional<UserProfile> findUserProfile = databaseUserProfileRepository.findProfileByUserId(like.getUser().getId());
        if(findUserProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        UserProfile likeUserProfile = findUserProfile.get();

        return new LikeResponse(like.getId(), post_id, like.getUser().getId(), likeUserProfile.getNickname(), likeUserProfile.getProfileImage());
    }

    @Override
    public Integer getLikeCount(Long post_id){
        Optional<Post> findPost = databasePostRepository.findPostById(post_id);

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

        databaseLikeRepository.deleteLikeOfPostByUserId(post_id, req.getUser_id());
        databasePostRepository.unlikeBySomeone(post_id);

        Optional<UserProfile> findUserProfile = databaseUserProfileRepository.findProfileByUserId(unlike.getUser().getId());
        if(findUserProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        UserProfile unlikeUserProfile = findUserProfile.get();

        return new LikeResponse(unlike.getId(), post_id, req.getUser_id(), unlikeUserProfile.getNickname(), unlikeUserProfile.getProfileImage());
    }

    @Override
    @Transactional
    public LikeListResponse getLikes(Long post_id){

        if(databasePostRepository.findPostById(post_id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        List<Like> likes = databaseLikeRepository.findLikesByPostId(post_id);

        List<User> likeUser = new ArrayList<>();
        for(Like like : likes){
            likeUser.add(like.getUser());
        }


        List<LikeResponse> data = likes.stream().map(like -> {
            Optional<UserProfile> likeProfile = databaseUserProfileRepository.findProfileByUserId(like.getUser().getId());
            if (likeProfile.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
            }

            return new LikeResponse(like.getId(), post_id, like.getUser().getId(), likeProfile.get().getNickname(), likeProfile.get().getProfileImage());
        }).toList();

        return new LikeListResponse(data);
    }
}
