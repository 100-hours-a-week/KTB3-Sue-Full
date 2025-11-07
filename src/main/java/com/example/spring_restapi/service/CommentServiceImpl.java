package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.response.CommentListResponse;
import com.example.spring_restapi.dto.response.CommentResponse;
import com.example.spring_restapi.model.Comment;
import com.example.spring_restapi.dto.request.CreateCommentRequest;
import com.example.spring_restapi.dto.request.UpdateCommentRequest;
import com.example.spring_restapi.model.Post;
import com.example.spring_restapi.model.User;
import com.example.spring_restapi.model.UserProfile;
import com.example.spring_restapi.repository.CommentRepository;
import com.example.spring_restapi.repository.PostRepository;
import com.example.spring_restapi.repository.UserProfileRepository;
import com.example.spring_restapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository databaseCommentRepository;
    private final PostRepository databasePostRepository;
    private final UserRepository databaseUserRepository;
    private final UserProfileRepository databaseUserProfileRepository;

    @Override
    public CommentListResponse getCommentsByPostId(Long post_id){
        if(databasePostRepository.findPostById(post_id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        List<Comment> comments = databaseCommentRepository.findCommentsByPostId(post_id);
        List<CommentResponse> data = comments.stream().map(
                comment -> {
                    Optional<UserProfile> findProfile = databaseUserProfileRepository.findProfileByUserId(comment.getUser().getId());
                    if(findProfile.isEmpty()){
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
                    }

                    return new CommentResponse(comment.getId(), post_id, comment.getUser().getId(), comment.getContent(), findProfile.get().getNickname(), findProfile.get().getProfileImage());
                }).toList();

        return new CommentListResponse(post_id, data);
    }

    @Override
    @Transactional
    public CommentResponse writeComment(Long post_id, CreateCommentRequest req){
        Optional<Post> findPost = databasePostRepository.findPostById(post_id);
        if(findPost.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Optional<User> findUser = databaseUserRepository.findUserById(req.getUser_id());

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Comment newComment = new Comment(findPost.get(), findUser.get(), req.getContent());

        databaseCommentRepository.save(newComment);
        databasePostRepository.writeCommentBySomeone(findPost.get().getId());

        Optional<UserProfile> findProfile = databaseUserProfileRepository.findProfileByUserId(newComment.getUser().getId());
        if(findProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        return new CommentResponse(newComment.getId(), post_id, newComment.getUser().getId(), newComment.getContent(), findProfile.get().getNickname(), findProfile.get().getProfileImage());
    }

    @Override
    @Transactional
    public CommentResponse updateComment(Long post_id, Long comment_id, UpdateCommentRequest req){
        Optional<Post> findPost = databasePostRepository.findPostById(post_id);
        if(findPost.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Optional<User> findUser = databaseUserRepository.findUserById(req.getUser_id());

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Optional<Comment> find = databaseCommentRepository.findCommentById(comment_id);

        if(find.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Comment update = find.get();
        update.changeContent(req.getContent());

        databaseCommentRepository.updateComment(update.getId(), update.getContent());


        Optional<UserProfile> findProfile = databaseUserProfileRepository.findProfileByUserId(update.getUser().getId());
        if(findProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        return new CommentResponse(update.getId(), findPost.get().getId(), req.getUser_id(), req.getContent(), findProfile.get().getNickname(), findProfile.get().getProfileImage());
    }

    @Override
    @Transactional
    public CommentResponse deleteComment(Long post_id, Long comment_id, Long user_id){
        if(databasePostRepository.findPostById(post_id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Post findPost = databasePostRepository.findPostById(post_id).get();

        Optional<Comment> find = databaseCommentRepository.findCommentById(comment_id);

        if(find.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        if(!find.get().getUser().getId().equals(user_id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }



        Comment comment = find.get();

        databaseCommentRepository.deleteComment(comment.getId());

        Optional<UserProfile> findProfile = databaseUserProfileRepository.findProfileByUserId(comment.getUser().getId());
        if(findProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        return new CommentResponse(comment.getId(), findPost.getId(), comment.getUser().getId(), comment.getContent(), findProfile.get().getNickname(), findProfile.get().getProfileImage());
    }

}

