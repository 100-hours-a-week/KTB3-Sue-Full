package com.example.spring_restapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@SequenceGenerator(
        name = "post_images_seq",
        sequenceName = "post_images_seq",
        allocationSize = 50
)
@Table(name = "post_images")
public class PostImages {

    @Schema(description = "게시글 이미지 아이디", example = "1L")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_images_seq")
    @Column(name = "post_images_id", nullable = false)
    private Long id;

    @Schema(description = "게시글", example = "1L")
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Schema(description = "게시글 콘텐츠 이미지", example = "post1.jpg")
    @Column(name = "image_url", nullable = false)
    private String image_url;

    @Schema(description = "게시글 썸네일 여부", example = "false")
    @Column(name = "is_thumbnail", nullable = false)
    private Boolean isThumbnail;


    @Schema(description = "게시글 이미지 생성일자", example = "20251020T10:00:00")
    @Setter
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Schema(description = "게시글 이미지 수정일자", example = "20251020T10:00:00")
    @Setter
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Schema(description = "게시글 이미지 삭제일자", example = "20251020T10:00:00")
    @Setter
    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;


    protected PostImages() {}

    public PostImages(Post post, String image_url, Boolean isThumbnail){
        if (post == null) throw new IllegalArgumentException("post's id is required");
        if (image_url == null || image_url.isBlank()) throw new IllegalArgumentException("image's url is required");

        this.post = post;
        this.image_url = image_url;

        this.isThumbnail = isThumbnail;

        // default value
        this.createdAt = LocalDateTime.now();
    }

    public static PostImages create(Post post, String image_url, Boolean isThumbnail){
        return new PostImages(post, image_url, isThumbnail);
    }
    public void changeImageUrl(String image_url) {
        if (image_url == null || image_url.isBlank()) throw new IllegalArgumentException("new image url is required");
        this.image_url = image_url;
    }

    public void changeIsThumbNail() { this.isThumbnail = !this.isThumbnail; }
}
