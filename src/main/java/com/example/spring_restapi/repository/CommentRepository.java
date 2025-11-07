package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    void save(Comment comment);

    Optional<Comment> update(Comment comment);

    Optional<Comment> deleteComment(Comment comment);

    List<Comment> deleteCommentByPostId(Long post_id);

    Optional<Comment> findCommentByCommentId(Long comment_id);

    List<Comment> findCommentsByPosId(Long post_id);
}
