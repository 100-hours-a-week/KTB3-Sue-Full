package com.example.spring_restapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@SequenceGenerator(
        name = "comment_seq",
        sequenceName = "comment_seq",
        allocationSize = 50
)
@Table(name = "comments")
public class Comment {
    @Schema(description = "댓글 아이디", example = "2L")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    @Column(name = "comment_id", nullable = false)
    private Long id;

    @Schema(description = "게시글", example = "1L")
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Schema(description = "작성자", example = "3L")
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Schema(description = "댓글 내용", example = "cool...")
    @Column(name = "content", length = 200)
    private String content;

    @Schema(description = "댓글 작성일자", example = "20251020T10:00:00")
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Schema(description = "댓글 수정일자", example = "20251020T10:00:00")
    @Setter
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Schema(description = "댓글 삭제일자", example = "20251020T10:00:00")
    @Setter
    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    protected Comment() {}

    public Comment(Post post, User user, String content){
        if (post == null) throw new IllegalArgumentException("post is required");
        if (user == null) throw new IllegalArgumentException("user is required");

        this.post = post;
        this.user = user;

        this.content = content;

        // default value
        this.createdAt = LocalDateTime.now();
    }

    public static Comment create(Post post, User user, String content){
        return new Comment(post, user, content);
    }

    public void changeContent(String content) {
        if (content == null || content.isBlank()) throw new IllegalArgumentException("new content is required");
        this.content = content;
    }

}
