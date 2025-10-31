package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.CreateCommentRequest;
import com.example.spring_restapi.dto.request.UpdateCommentRequest;
import com.example.spring_restapi.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getCommentsByPostId(Long post_id);

    Comment writeComment(Long post_id, CreateCommentRequest req);

    Comment updateComment(Long post_id, Long comment_id, UpdateCommentRequest req);

    Comment deleteComment(Long post_id, Long comment_id, Long user_id);

}
