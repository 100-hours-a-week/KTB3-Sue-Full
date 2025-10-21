package com.example.spring_restapi.controller;

import com.example.spring_restapi.model.Post;
import com.example.spring_restapi.dto.request.CreatePostRequest;
import com.example.spring_restapi.dto.request.DeletePostRequest;
import com.example.spring_restapi.dto.request.UserIdBodyRequest;
import com.example.spring_restapi.dto.request.UpdatePostRequest;
import com.example.spring_restapi.dto.response.CommonResponse;
import com.example.spring_restapi.dto.response.ReadPostByPageResponse;
import com.example.spring_restapi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @Operation(summary = "게시물 조회", description = "한 페이지당 최대 10개의 게시물을 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 조회 성공"),
            @ApiResponse(responseCode = "400", description = "페이지가 0이거나 10개 이상의 게시물을 조회 시도")
    })
    @GetMapping
    public ResponseEntity<CommonResponse<ReadPostByPageResponse>> readPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        List<Post> posts = postService.getPostsOfPage(page,size);

        ReadPostByPageResponse data = new ReadPostByPageResponse(posts);
        CommonResponse<ReadPostByPageResponse> res = CommonResponse.success("read_posts_success", data);
        return ResponseEntity.ok(res);
        //request, response DTO 만들
    }

    @Operation(summary = "특정 게시물 조회", description = "게시물 아이디를 이용하여 게시물 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물임")
    })
    @GetMapping("/{post_id}")
    public ResponseEntity<CommonResponse<Post>> readPostsById(@PathVariable Long post_id){
        Post data = postService.getPostByPostId(post_id);

        CommonResponse<Post> res = CommonResponse.success("read_post_success", data);
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "게시물 작성", description = "새로운 게시물을 시스템에 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시물 작성 성공")
    })
    @PostMapping
    public ResponseEntity<CommonResponse<Post>> writePost(@RequestBody CreatePostRequest req){
        Post data = postService.write(req);

        CommonResponse<Post> res = CommonResponse.success("write_post_success", data);
        return ResponseEntity.status(201).body(res);
    }

    @Operation(summary = "게시물 수정", description = "시스템에 등록된 게시물을 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시물 수정 완료"),
            @ApiResponse(responseCode = "400", description = "게시물 작성자가 아닌 유저가 게시물을 수정하려 함"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물을 수정하려 함")
    })
    @PatchMapping("/{post_id}")
    public ResponseEntity<CommonResponse<Post>> updatePost(@PathVariable Long post_id, @RequestBody UpdatePostRequest req){
        Post data = postService.updatePost(post_id, req);

        CommonResponse<Post> res = CommonResponse.success("update_post_success", data);

        return ResponseEntity.status(201).body(res);
    }

    @Operation(summary = "게시물 삭제", description = "시스템에 등록된 게시물을 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "게시물 작성자가 아닌 유저가 게시물을 삭제하려 함"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물을 삭제하려 함")
    })
    @DeleteMapping("/{post_id}")
    public ResponseEntity<CommonResponse<Long>> deletePost(@PathVariable Long post_id, @RequestBody DeletePostRequest req){
        Post data = postService.deletePost(post_id, req.getUser_id());

        CommonResponse<Long> res = CommonResponse.success("delete_post_success", data.getPost_id());

        return ResponseEntity.ok(res);
    }

    @Operation(summary = "좋아요 수 조회", description = "특정 게시물의 좋아요 수를 조회함")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 수 조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물임")
    })
    @GetMapping("/{post_id}/likes")
    public ResponseEntity<CommonResponse<Integer>> getLikeCount(@PathVariable Long post_id){
        int data = postService.getLikeCount(post_id);

        CommonResponse<Integer> res = CommonResponse.success("read_like_success", data);

        return ResponseEntity.ok(res);
    }


    @Operation(summary = "좋아요 누르기", description = "게시물에 좋아요를 누름")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "좋아요 성공"),
            @ApiResponse(responseCode = "409", description = "이미 좋아요를 누른 게시물임")
    })
    @PostMapping("/{post_id}/likes")
    public ResponseEntity<CommonResponse<Post>> like(@PathVariable Long post_id, @RequestBody UserIdBodyRequest req){
        Post data = postService.like(post_id, req.getUser_id());

        CommonResponse<Post> res = CommonResponse.success("like_success", data);

        return ResponseEntity.status(201).body(res);
    }

    @Operation(summary = "좋아요 취소", description = "좋아요를 했던 게시물의 좋아요를 취소함")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 취소 성공"),
            @ApiResponse(responseCode = "400", description = "좋아요를 누르지 않았던 게시물임"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물임")
    })
    @DeleteMapping("/{post_id}/likes")
    public ResponseEntity<CommonResponse<Long>> unlike(@PathVariable Long post_id, @RequestBody UserIdBodyRequest req){
        Post data = postService.unlike(post_id, req.getUser_id());

        CommonResponse<Long> res = CommonResponse.success("unlike_success", data.getPost_id());

        return ResponseEntity.ok(res);
    }


}
