package com.example.spring_restapi.controller;

import com.example.spring_restapi.dto.request.CreatePostRequest;
import com.example.spring_restapi.dto.request.DeletePostRequest;
import com.example.spring_restapi.dto.request.UpdatePostRequest;
import com.example.spring_restapi.dto.response.CommonResponse;
import com.example.spring_restapi.dto.response.PostResponse;
import com.example.spring_restapi.dto.response.ReadPostByPageResponse;
import com.example.spring_restapi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postServiceImpl;

    public PostController(PostService postServiceImpl){
        this.postServiceImpl = postServiceImpl;
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

        ReadPostByPageResponse data = new ReadPostByPageResponse(postServiceImpl.getPostsOfPage(page,size));
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
    public ResponseEntity<CommonResponse<PostResponse>> readPostsById(@PathVariable Long post_id){
        PostResponse data = postServiceImpl.getPostByPostId(post_id);

        CommonResponse<PostResponse> res = CommonResponse.success("read_post_success", data);
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "게시물 작성", description = "새로운 게시물을 시스템에 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시물 작성 성공")
    })
    @PostMapping
    public ResponseEntity<CommonResponse<PostResponse>> writePost(@RequestBody CreatePostRequest req){
        PostResponse data = postServiceImpl.write(req);

        CommonResponse<PostResponse> res = CommonResponse.success("write_post_success", data);
        return ResponseEntity.status(201).body(res);
    }

    @Operation(summary = "게시물 수정", description = "시스템에 등록된 게시물을 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시물 수정 완료"),
            @ApiResponse(responseCode = "400", description = "게시물 작성자가 아닌 유저가 게시물을 수정하려 함"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물을 수정하려 함")
    })
    @PatchMapping("/{post_id}")
    public ResponseEntity<CommonResponse<PostResponse>> updatePost(@PathVariable Long post_id, @RequestBody UpdatePostRequest req){
        PostResponse data = postServiceImpl.updatePost(post_id, req);

        CommonResponse<PostResponse> res = CommonResponse.success("update_post_success", data);

        return ResponseEntity.status(201).body(res);
    }

    @Operation(summary = "게시물 삭제", description = "시스템에 등록된 게시물을 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "게시물 작성자가 아닌 유저가 게시물을 삭제하려 함"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물을 삭제하려 함")
    })
    @DeleteMapping("/{post_id}")
    public ResponseEntity<CommonResponse<PostResponse>> deletePost(@PathVariable Long post_id, @RequestParam Long user_id){
        PostResponse data = postServiceImpl.deletePost(post_id, user_id);

        CommonResponse<PostResponse> res = CommonResponse.success("delete_post_success", data);

        return ResponseEntity.ok(res);
    }

}
