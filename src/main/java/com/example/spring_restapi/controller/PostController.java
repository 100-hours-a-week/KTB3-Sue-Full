package com.example.spring_restapi.controller;

import com.example.spring_restapi.dto.request.CreatePostRequest;
import com.example.spring_restapi.dto.request.DeletePostRequest;
import com.example.spring_restapi.dto.request.UpdatePostRequest;
import com.example.spring_restapi.dto.request.UserIdBodyRequest;
import com.example.spring_restapi.dto.response.CommonResponse;
import com.example.spring_restapi.dto.response.PostResponse;
import com.example.spring_restapi.model.Post;
import com.example.spring_restapi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postServiceImpl;

    @Operation(summary = "게시물 조회", description = "한 페이지당 최대 10개의 게시물을 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 조회 성공"),
            @ApiResponse(responseCode = "400", description = "페이지가 0이거나 10개 이상의 게시물을 조회 시도")
    })
    @GetMapping
    public ResponseEntity<CommonResponse<Page<PostResponse>>> readPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){

        Page<PostResponse> data = postServiceImpl.getPostsOfPage(page,size);
        CommonResponse<Page<PostResponse>> res = CommonResponse.success("read_posts_success", data);
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

    @Operation(summary = "특정 유저가 작성한 게시물 조회", description = "작성자 아이디를 이용하여 게시물 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물임")
    })
    @GetMapping("/author/{user_id}")
    public ResponseEntity<CommonResponse<Page<PostResponse>>> readPostsByAuthorId(
            @PathVariable Long user_id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<PostResponse> data= postServiceImpl.getPostByAuthorId(user_id, page, size);

        CommonResponse<Page<PostResponse>> res = CommonResponse.success("read_post_success", data);
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "게시물 작성자 여부 체크", description = "게시물이 주어진 유저에 의해 쓰였는지 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 작성 여부 체크 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시물임")
    })
    @GetMapping("/{post_id}/check")
    public ResponseEntity<CommonResponse<Boolean>> checkPostingByUser(@PathVariable Long post_id, @RequestParam Long user_id){
        Boolean data = postServiceImpl.checkPostingByUser(post_id, user_id);

        CommonResponse<Boolean> res = CommonResponse.success("check_post_written_by_user_success", data);
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "게시물 작성", description = "새로운 게시물을 시스템에 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시물 작성 성공")
    })
    @PostMapping
    public ResponseEntity<CommonResponse<PostResponse>> writePost(@ModelAttribute CreatePostRequest req) throws IOException {
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
    public ResponseEntity<CommonResponse<PostResponse>> updatePost(@PathVariable Long post_id, @ModelAttribute UpdatePostRequest req) throws IOException {
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
    public ResponseEntity<CommonResponse<PostResponse>> deletePost(@PathVariable Long post_id, @RequestBody DeletePostRequest req){
        PostResponse data = postServiceImpl.deletePost(post_id, req.getUser_id());

        CommonResponse<PostResponse> res = CommonResponse.success("delete_post_success", data);

        return ResponseEntity.ok(res);
    }

    // pageable
    @GetMapping("/search/list")
    public ResponseEntity<CommonResponse<List<PostResponse>>> searchAsList(@RequestParam String keyword) {
        List<PostResponse> data =  postServiceImpl.searchAsList(keyword);

        CommonResponse<List<PostResponse>> res = CommonResponse.success("find_post_by_keyword_success_list", data);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/search/page")
    public ResponseEntity<CommonResponse<Page<PostResponse>>> searchAsPage(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Page<PostResponse> data = postServiceImpl.searchAsPage(keyword, page, size, sortBy, direction);

        CommonResponse<Page<PostResponse>> res = CommonResponse.success("find_post_by_keyword_success_page", data);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/search/slice")
    public ResponseEntity<CommonResponse<Slice<PostResponse>>> searchAsSlice(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Slice<PostResponse> data = postServiceImpl.searchAsSlice(keyword, page, size, sortBy, direction);

        CommonResponse<Slice<PostResponse>> res = CommonResponse.success("find_post_by_keyword_success_slice", data);

        return ResponseEntity.ok(res);
    }
}
