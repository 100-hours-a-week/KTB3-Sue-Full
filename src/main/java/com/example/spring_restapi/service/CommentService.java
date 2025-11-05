package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.CreateCommentRequest;
import com.example.spring_restapi.dto.request.UpdateCommentRequest;
import com.example.spring_restapi.dto.response.CommentListResponse;
import com.example.spring_restapi.dto.response.CommentResponse;
import com.example.spring_restapi.model.Comment;

import java.util.List;

public interface CommentService {

    CommentListResponse getCommentsByPostId(Long post_id);

    CommentResponse writeComment(Long post_id, CreateCommentRequest req);

    CommentResponse updateComment(Long post_id, Long comment_id, UpdateCommentRequest req);

    CommentResponse deleteComment(Long post_id, Long comment_id, Long user_id);

}
