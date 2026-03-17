package com.example.spring_restapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@SequenceGenerator(
        name = "like_seq",
        sequenceName = "like_seq",
        allocationSize = 30
)
@Table(name = "likes")
public class Like extends AbstractAuditable {

    @Schema(description = "좋아요 아이디", example = "3L")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_seq")
    @Column(name = "like_id", nullable = false)
    private Long id;

    @Schema(description = "게시글", example = "1L")
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Schema(description = "게시글에 좋아요를 누른 유저", example = "1L")
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Schema(description = "좋아요 생성일자", example = "20251020T10:00:00")
    @CreatedDate
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Schema(description = "좋아요 수정일자", example = "20251020T10:00:00")
    @LastModifiedDate
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Schema(description = "좋아요 삭제일자", example = "20251020T10:00:00")
    @Setter
    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;


    protected Like() {}

    public Like(Post post, User user){
        if (post == null) throw new IllegalArgumentException("post is required");
        if (user == null) throw new IllegalArgumentException("user is required");

        this.post = post;
        this.user = user;

        // default value
        this.createdAt = LocalDateTime.now();
    }

    public static Like create(Post post, User user) {
        return new Like(post, user);
    }
}
