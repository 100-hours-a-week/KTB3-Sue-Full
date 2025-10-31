package com.example.spring_restapi.service;

import com.example.spring_restapi.model.Comment;
import com.example.spring_restapi.dto.request.CreateCommentRequest;
import com.example.spring_restapi.dto.request.UpdateCommentRequest;
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
    public List<Comment> getCommentsByPostId(Long post_id){
        if(databasePostRepository.findPostByPostId(post_id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        return databaseCommentRepository.findCommentByPosId(post_id);
    }

    @Override
    public Comment writeComment(Long post_id, CreateCommentRequest req){
        if(databasePostRepository.findPostByPostId(post_id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        if(databaseUserRepository.findUserById(req.getAuthor_id()).isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Comment newComment = new Comment(null, post_id, req.getAuthor_id(), req.getContent(), LocalDateTime.now());

        return databaseCommentRepository.save(newComment);
    }

    @Override
    public Comment updateComment(Long post_id, Long comment_id, UpdateCommentRequest req){
        if(databasePostRepository.findPostByPostId(post_id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        if(databaseUserRepository.findUserById(req.getAuthor_id()).isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Optional<Comment> find = databaseCommentRepository.findCommentByCommentId(comment_id);

        if(find.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Comment update = find.get();
        update.setContent(req.getContent());
        update.setRewriteDate(LocalDateTime.now());

        Optional<Comment> updateComment = databaseCommentRepository.update(update);

        if(updateComment.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        return updateComment.get();
    }

    @Override
    public Comment deleteComment(Long post_id, Long comment_id, Long user_id){
        if(databasePostRepository.findPostByPostId(post_id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Optional<Comment> find = databaseCommentRepository.findCommentByCommentId(comment_id);

        if(find.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        if(!find.get().getAuthor_id().equals(user_id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        return databaseCommentRepository.deleteComment(find.get());
    }

}

