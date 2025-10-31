package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> update(Comment comment);

    Comment deleteComment(Comment comment);

    Optional<Comment> findCommentByCommentId(Long comment_id);

    List<Comment> findCommentByPosId(Long post_id);
}
