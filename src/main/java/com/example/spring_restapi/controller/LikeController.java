package com.example.spring_restapi.controller;

import com.example.spring_restapi.dto.request.UserIdBodyRequest;
import com.example.spring_restapi.dto.response.CommonResponse;
import com.example.spring_restapi.dto.response.LikeResponse;
import com.example.spring_restapi.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{post_id}/likes")
public class LikeController {
    private final LikeService likeServiceImpl;

    public LikeController(LikeService likeServiceImpl){
        this.likeServiceImpl = likeServiceImpl;
    }

    @Operation(summary = "좋아요 누르기", description = "게시물에 좋아요를 누름")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "좋아요 성공"),
            @ApiResponse(responseCode = "409", description = "이미 좋아요를 누른 게시물임")
    })
    @PostMapping
    public ResponseEntity<CommonResponse<LikeResponse>> like(
            @PathVariable Long post_id,
            @RequestBody UserIdBodyRequest req
            ){
        LikeResponse like = likeServiceImpl.like(post_id, req);

        CommonResponse<LikeResponse> res = CommonResponse.success("like_success", like);
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
        int likeCount = likeServiceImpl.getLikeCount(post_id);

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
    public ResponseEntity<CommonResponse<LikeResponse>> unlike(
            @PathVariable Long post_id,
            @RequestBody UserIdBodyRequest req
    ){
        LikeResponse like = likeServiceImpl.unlike(post_id, req);

        CommonResponse<LikeResponse> res = CommonResponse.success("unlike_success", like);
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "좋아요 목록", description = "게시물의 좋아요 목록을 가져옴")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 목록 로딩 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물임")
    })
    @GetMapping("/list")
    public ResponseEntity<CommonResponse<Slice<LikeResponse>>> getLikes(
            @PathVariable Long post_id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String direction
    ){
        Slice<LikeResponse >like = likeServiceImpl.getLikes(post_id, page, size, direction);

        CommonResponse<Slice<LikeResponse>> res = CommonResponse.success("read_like_list_success", like);
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "좋아요 여부 체크", description = "유저의 게시물 좋아요 어부를 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 여부 체크 성공")
    })
    @GetMapping("/check")
    public ResponseEntity<CommonResponse<Boolean>> checkLikeByUser(
            @PathVariable Long post_id,
            @RequestParam Long user_id
    ){
        Boolean data = likeServiceImpl.checkLikedByUser(post_id, user_id);

        CommonResponse<Boolean> res = CommonResponse.success("check_like_success", data);
        return ResponseEntity.ok(res);
    }
}