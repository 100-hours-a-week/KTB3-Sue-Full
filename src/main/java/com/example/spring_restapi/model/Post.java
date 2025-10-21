package com.example.spring_restapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Post {
    @Schema(description = "게시글 아이디", example = "1L")
    private Long post_id;

    @Schema(description = "게시글 작성자 아이디", example = "2L")
    private Long author_id;

    @Schema(description = "게시글 제목", example = "TIL")
    private String title;

    @Schema(description = "게시글 내용", example = "오늘은 Swagger에 대해 배웠다...")
    private String content;

    @Schema(description = "게시글 콘텐츠 이미지", example = "[post1.jpg, post2.jpg]")
    private List<String> images;

    @Schema(description = "게시글에 좋아요를 누른 유저들의 아이디 세트", example = "[1L, 2L, 3L]")
    private final Set<Long> likedUserIds;

    @Schema(description = "게시글의 조회수", example = "5")
    private Integer watch;

    @Schema(description = "게시글 작성 일자", example = "20251020T10:00:00")
    private LocalDateTime date;

    @Schema(description = "게시글 수정 일자", example = "20251022T10:00:00")
    private LocalDateTime rewriteDate;


    public Post(Long post_id, Long author_id, String title, String content, List<String> images, Set<Long> likedUserIds, Integer watch, LocalDateTime date){
        this.post_id = post_id;
        this.author_id = author_id;
        this.title = title;
        this.content = content;
        this.images = images;
        this.likedUserIds = likedUserIds;
        this.watch = watch;
        this.date = date;
    }
    // Setter
    public void setPost_id(Long post_id){
        this.post_id = post_id;
    }

    public void setAuthor_id(Long author_id){
        this.author_id = author_id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setImages(List<String> images){
        this.images = images;
    }

    public void like(Long likeUserId) { likedUserIds.add(likeUserId); }

    public void unlike(Long unlikeUserId) { likedUserIds.remove(unlikeUserId); }

    public void setWatch(Integer watch){
        this.watch = watch;
    }

    public void setDate(LocalDateTime date){
        this.date = date;
    }

    public void setRewriteDate(LocalDateTime rewriteDate){
        this.rewriteDate = rewriteDate;
    }

    // Getter
    public Long getPost_id(){ return post_id; }

    public Long getAuthor_id() { return author_id; }

    public String getTitle() { return title; }

    public String getContent() { return content; }

    public List<String> getImages() { return images; }

    public Set<Long> getLikedUserIds() { return likedUserIds; }

    public Integer getLikeCount() { return likedUserIds.size(); }

    public Boolean isLikedByUser(Long user_id) { return likedUserIds.contains(user_id); }

    public Integer getWatch() { return watch; }

    public LocalDateTime getDate() { return date; }
}
