package com.example.spring_restapi.controller;

import com.example.spring_restapi.model.Comment;
import com.example.spring_restapi.dto.request.CreateCommentRequest;
import com.example.spring_restapi.dto.request.UpdateCommentRequest;
import com.example.spring_restapi.dto.request.UserIdBodyRequest;
import com.example.spring_restapi.dto.response.CommonResponse;
import com.example.spring_restapi.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{post_id}/comments")
public class CommentController {
    private final CommentService commentServiceImpl;

    public CommentController(CommentService commentServiceImpl){
        this.commentServiceImpl = commentServiceImpl;
    }

    @Operation(summary = "댓글 조회", description = "특정 게시물의 댓글을 조회함")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물임")
    })
    @GetMapping
    public ResponseEntity<CommonResponse<List<Comment>>> readCommentsByPostId(@PathVariable Long post_id){
        List<Comment> data = commentServiceImpl.getCommentsByPostId(post_id);

        CommonResponse<List<Comment>> res = CommonResponse.success("read_comments_success", data);

        return ResponseEntity.ok(res);
    }

    @Operation(summary = "댓글 작성", description = "게시물에 댓글을 작성함")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "댓글 작성 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물임")
    })
    @PostMapping
    public ResponseEntity<CommonResponse<Comment>> writeComment(@PathVariable Long post_id, @RequestBody CreateCommentRequest req){
        Comment data = commentServiceImpl.writeComment(post_id, req);

        CommonResponse<Comment> res = CommonResponse.success("write_comment_success", data);

        return ResponseEntity.status(201).body(res);
    }

    @Operation(summary = "댓글 수정", description = "작성한 댓글을 수정함")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "댓글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "댓글 작성자가 아닌 유저가 수정 시도함"),
            @ApiResponse(responseCode = "404", description = "게시물이 존재하지 않거나, 댓글이 존재하지 않음")
    })
    @PutMapping("/{comment_id}")
    public ResponseEntity<CommonResponse<Comment>> updateComment(@PathVariable Long post_id, @PathVariable Long comment_id, @RequestBody UpdateCommentRequest req){
        Comment data = commentServiceImpl.updateComment(post_id, comment_id, req);

        CommonResponse<Comment> res = CommonResponse.success("update_comment_success", data);

        return ResponseEntity.status(201).body(res);
    }

    @Operation(summary = "댓글 삭제", description = "작성한 댓글을 삭제함")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "댓글 작성자가 아닌 유저가 삭제 시도함"),
            @ApiResponse(responseCode = "404", description = "게시물이 존재하지 않거나, 댓글이 존재하지 않음")
    })
    @DeleteMapping("/{comment_id}")
    public ResponseEntity<CommonResponse<Comment>> deleteComment(@PathVariable Long post_id, @PathVariable Long comment_id, @RequestBody UserIdBodyRequest req){
        Comment data = commentServiceImpl.deleteComment(post_id, comment_id, req.getUser_id());

        CommonResponse<Comment> res = CommonResponse.success("delete_comment_success", data);

        return ResponseEntity.ok(res);
    }
}
