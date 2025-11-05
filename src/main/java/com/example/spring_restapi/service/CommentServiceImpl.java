package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.response.CommentListResponse;
import com.example.spring_restapi.dto.response.CommentResponse;
import com.example.spring_restapi.model.Comment;
import com.example.spring_restapi.dto.request.CreateCommentRequest;
import com.example.spring_restapi.dto.request.UpdateCommentRequest;
import com.example.spring_restapi.model.Post;
import com.example.spring_restapi.model.User;
import com.example.spring_restapi.repository.CommentRepository;
import com.example.spring_restapi.repository.PostRepository;
import com.example.spring_restapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository databaseCommentRepository;
    private final PostRepository databasePostRepository;
    private final UserRepository databaseUserRepository;

    public CommentServiceImpl(CommentRepository databaseCommentRepository, PostRepository databasePostRepository, UserRepository databaseUserRepository){
        this.databaseCommentRepository = databaseCommentRepository;
        this.databasePostRepository = databasePostRepository;
        this.databaseUserRepository = databaseUserRepository;
    }

    @Override
    public CommentListResponse getCommentsByPostId(Long post_id){
        if(databasePostRepository.findPostByPostId(post_id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        List<Comment> comments = databaseCommentRepository.findCommentsByPosId(post_id);
        List<CommentResponse> data = comments.stream().map(comment -> new CommentResponse(comment.getId(), post_id, comment.getUser().getId(), comment.getContent())).toList();

        return new CommentListResponse(post_id, data);
    }

    @Override
    public CommentResponse writeComment(Long post_id, CreateCommentRequest req){
        Optional<Post> findPost = databasePostRepository.findPostByPostId(post_id);
        if(findPost.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Optional<User> findUser = databaseUserRepository.findUserById(req.getUser_id());

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Comment newComment = new Comment(findPost.get(), findUser.get(), req.getContent());

        databaseCommentRepository.save(newComment);
        databasePostRepository.writeCommentBySomeone(findPost.get());

        return new CommentResponse(newComment.getId(), post_id, newComment.getUser().getId(), newComment.getContent());
    }

    @Override
    public CommentResponse updateComment(Long post_id, Long comment_id, UpdateCommentRequest req){
        Optional<Post> findPost = databasePostRepository.findPostByPostId(post_id);
        if(findPost.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Optional<User> findUser = databaseUserRepository.findUserById(req.getUser_id());

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Optional<Comment> find = databaseCommentRepository.findCommentByCommentId(comment_id);

        if(find.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Comment update = find.get();
        update.changeContent(req.getContent());
        update.setUpdatedAt(LocalDateTime.now());

        Optional<Comment> updateComment = databaseCommentRepository.update(update);

        if(updateComment.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Comment comment = updateComment.get();

        return new CommentResponse(comment.getId(), findPost.get().getId(), req.getUser_id(), req.getContent());
    }

    @Override
    public CommentResponse deleteComment(Long post_id, Long comment_id, Long user_id){
        if(databasePostRepository.findPostByPostId(post_id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Post findPost = databasePostRepository.findPostByPostId(post_id).get();

        Optional<Comment> find = databaseCommentRepository.findCommentByCommentId(comment_id);

        if(find.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        if(!find.get().getUser().getId().equals(user_id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Optional<Comment> deleteComment = databaseCommentRepository.deleteComment(find.get());

        if(deleteComment.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Comment comment = deleteComment.get();
        return new CommentResponse(comment.getId(), findPost.getId(), comment.getUser().getId(), comment.getContent());
    }

}

