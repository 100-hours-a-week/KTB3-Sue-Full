package com.example.spring_restapi.controller;

import com.example.spring_restapi.dto.Post;
import com.example.spring_restapi.dto.request.CreatePostRequest;
import com.example.spring_restapi.dto.request.DeletePostRequest;
import com.example.spring_restapi.dto.request.UserIdBodyRequest;
import com.example.spring_restapi.dto.request.UpdatePostRequest;
import com.example.spring_restapi.dto.response.CommonResponse;
import com.example.spring_restapi.dto.response.ReadPostByPageResponse;
import com.example.spring_restapi.service.PostService;
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

    @GetMapping("/{post_id}")
    public ResponseEntity<CommonResponse<Post>> readPostsById(@PathVariable Long post_id){
        Post data = postService.getPostByPostId(post_id);

        CommonResponse<Post> res = CommonResponse.success("read_post_success", data);
        return ResponseEntity.ok(res);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<Post>> writePost(@RequestBody CreatePostRequest req){
        Post data = postService.write(req);

        CommonResponse<Post> res = CommonResponse.success("write_post_success", data);
        return ResponseEntity.status(201).body(res);
    }

    @PatchMapping("/{post_id}")
    public ResponseEntity<CommonResponse<Post>> updatePost(@PathVariable Long post_id, @RequestBody UpdatePostRequest req){
        Post data = postService.updatePost(post_id, req);

        CommonResponse<Post> res = CommonResponse.success("update_post_success", data);

        return ResponseEntity.status(201).body(res);
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<CommonResponse<Long>> deletePost(@PathVariable Long post_id, @RequestBody DeletePostRequest req){
        Post data = postService.deletePost(post_id, req.getUser_id());

        CommonResponse<Long> res = CommonResponse.success("delete_post_success", data.getPost_id());

        return ResponseEntity.ok(res);
    }

    @GetMapping("/{post_id}/likes")
    public ResponseEntity<CommonResponse<Integer>> getLikeCount(@PathVariable Long post_id){
        int data = postService.getLikeCount(post_id);

        CommonResponse<Integer> res = CommonResponse.success("read_like_success", data);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/{post_id}/likes")
    public ResponseEntity<CommonResponse<Post>> like(@PathVariable Long post_id, @RequestBody UserIdBodyRequest req){
        Post data = postService.like(post_id, req.getUser_id());

        CommonResponse<Post> res = CommonResponse.success("like_success", data);

        return ResponseEntity.status(201).body(res);
    }

    @DeleteMapping("/{post_id}/likes")
    public ResponseEntity<CommonResponse<Long>> unlike(@PathVariable Long post_id, @RequestBody UserIdBodyRequest req){
        Post data = postService.unlike(post_id, req.getUser_id());

        CommonResponse<Long> res = CommonResponse.success("unlike_success", data.getPost_id());

        return ResponseEntity.ok(res);
    }


}
