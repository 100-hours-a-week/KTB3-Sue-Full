package com.example.spring_restapi.controller;

import com.example.spring_restapi.dto.request.UserIdBodyRequest;
import com.example.spring_restapi.dto.response.CommonResponse;
import com.example.spring_restapi.model.Like;
import com.example.spring_restapi.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{post_id}/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService){
        this.likeService = likeService;
    }

    @Operation(summary = "좋아요 누르기", description = "게시물에 좋아요를 누름")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "좋아요 성공"),
            @ApiResponse(responseCode = "409", description = "이미 좋아요를 누른 게시물임")
    })
    @PostMapping
    public ResponseEntity<CommonResponse<Like>> like(
            @PathVariable Long post_id,
            @RequestBody UserIdBodyRequest req
            ){
        Like like = likeService.like(post_id, req);

        CommonResponse<Like> res = CommonResponse.success("like_success", like);
        return ResponseEntity.status(201).body(res);
    }

    @Operation(summary = "좋아요 수 조회", description = "특정 게시물의 좋아요 수를 조회함")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 수 조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물임")
    })
    @GetMapping
    public ResponseEntity<CommonResponse<Integer>> getLikeCount(
            @PathVariable Long post_id
    ){
        int likeCount = likeService.getLikeCount(post_id);

        CommonResponse<Integer> res = CommonResponse.success("read_like_success", likeCount);
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "좋아요 취소", description = "좋아요를 했던 게시물의 좋아요를 취소함")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 취소 성공"),
            @ApiResponse(responseCode = "400", description = "좋아요를 누르지 않았던 게시물임"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물임")
    })
    @DeleteMapping
    public ResponseEntity<CommonResponse<Like>> unlike(
            @PathVariable Long post_id,
            @RequestBody UserIdBodyRequest req
    ){
        Like like = likeService.unlike(post_id, req);

        CommonResponse<Like> res = CommonResponse.success("unlike_success", like);
        return ResponseEntity.ok(res);
    }
}
