package com.example.spring_restapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SequenceGenerator(
        name = "post_seq",
        sequenceName = "post_seq",
        allocationSize = 50
)
@Table(name = "posts")
public class Post {

    @Schema(description = "게시글 아이디", example = "1L")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
    @Column(name = "post_id", nullable = false)
    private Long id;

    @Schema(description = "게시글 작성자", example = "2L, ...")
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @Schema(description = "게시글 제목", example = "TIL")
    @Column(name = "title", length = 30)
    private String title;

    @Schema(description = "게시글 내용", example = "오늘은 Swagger에 대해 배웠다...")
    @Column(name = "content", length = 1000)
    private String content;

    @Schema(description = "게시글 카테고리", example = "NOTICE/FREE")
    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Schema(description = "게시글의 조회수", example = "5")
    @Column(name = "watch")
    private Integer watch;

    @Schema(description = "게시글의 좋아요 수", example = "3")
    @Column(name = "like_count")
    private Integer likeCount;

    @Schema(description = "게시글의 댓글 수", example = "1")
    @Column(name = "comment_count")
    private Integer commentCount;

    @Schema(description = "게시글 생성일자", example = "20251020T10:00:00")
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Schema(description = "게시글 수정일자", example = "20251020T10:00:00")
    @Setter
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Schema(description = "게시글 삭제일자", example = "20251020T10:00:00")
    @Setter
    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    protected Post() {}

    public Post(String title, String content, PostType postType, User author){
        if (author == null) throw new IllegalArgumentException("author is required");

        this.title = title;
        this.content = content;
        this.postType = postType;
        this.author = author;
        // default value
        this.watch = 0;
        this.likeCount = 0;
        this.commentCount = 0;
        this.createdAt = LocalDateTime.now();
    }

    public static Post create(Long author_id, String title, String content, PostType postType, User author){
        return new Post(title, content, postType, author);
    }

    public void changeTitle(String title){
        if (title == null || title.isBlank()) throw new IllegalArgumentException("new title is required");
        this.title = title;
    }

    public void changeContent(String content){
        if (content == null || content.isBlank()) throw new IllegalArgumentException("new content is required");
        this.content = content;
    }

    public void increaseWatch(){
        watch++;
    }

    public void increaseLikeCount(){
        likeCount++;
    }

    public void decreaseLikeCount(){
        likeCount--;
    }

    public void increaseCommentCount(){
        commentCount++;
    }

    public void decreaseCommentCount(){
        commentCount--;
    }

    public void changePostType(PostType postType) {
        this.postType = postType;
    }
}
