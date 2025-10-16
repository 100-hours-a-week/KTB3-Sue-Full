package com.example.spring_restapi.controller;

import com.example.spring_restapi.dto.Comment;
import com.example.spring_restapi.dto.request.CreateCommentRequest;
import com.example.spring_restapi.dto.request.UpdateCommentRequest;
import com.example.spring_restapi.dto.request.UserIdBodyRequest;
import com.example.spring_restapi.dto.response.CommonResponse;
import com.example.spring_restapi.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{post_id}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<Comment>>> readCommentsByPostId(@PathVariable Long post_id){
        List<Comment> data = commentService.getCommentsByPostId(post_id);

        CommonResponse<List<Comment>> res = CommonResponse.success("read_comments_success", data);

        return ResponseEntity.ok(res);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<Comment>> writeComment(@PathVariable Long post_id, @RequestBody CreateCommentRequest req){
        Comment data = commentService.writeComment(post_id, req);

        CommonResponse<Comment> res = CommonResponse.success("write_comment_success", data);

        return ResponseEntity.status(201).body(res);
    }

    @PutMapping("/{comment_id}")
    public ResponseEntity<CommonResponse<Comment>> updateComment(@PathVariable Long post_id, @PathVariable Long comment_id, @RequestBody UpdateCommentRequest req){
        Comment data = commentService.updateComment(post_id, comment_id, req);

        CommonResponse<Comment> res = CommonResponse.success("update_comment_success", data);

        return ResponseEntity.status(201).body(res);
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<CommonResponse<Comment>> deleteComment(@PathVariable Long post_id, @PathVariable Long comment_id, @RequestBody UserIdBodyRequest req){
        Comment data = commentService.deleteComment(post_id, comment_id, req.getUser_id());

        CommonResponse<Comment> res = CommonResponse.success("delete_comment_success", data);

        return ResponseEntity.ok(res);
    }
}
