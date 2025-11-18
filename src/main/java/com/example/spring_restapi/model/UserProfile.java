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
@Table(name = "user_profile")
@SequenceGenerator(
        name = "user_profile_seq",
        sequenceName = "user_profile_seq",
        allocationSize = 50
)
public class UserProfile extends AbstractAuditable {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_profile_seq")
    @Column(name = "user_profile_id", nullable = false)
    private Long id;

    @Schema(description = "사용자", example = "2L, email, password...")
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true) // 주인이 아님
    private User user;

    @Schema(description = "사용자 닉네임", example = "sue")
    @Column(name = "nickname", length = 50, unique = true)
    private String nickname;

    @Schema(description = "사용자 프로필 이미지", example = "profileImage.jpg")
    @Column(name = "profile_image")
    private String profileImage;

    @Schema(description = "사용자 소개말", example = "Hi, I'm sue")
    @Column(name = "introduce")
    private String introduce;

    @Schema(description = "사용자 성별", example = "F")
    @Column(name = "gender", length = 1)
    private String gender;

    @Schema(description = "사용자 계정 공개여부", example = "false")
    @Column(name = "is_private", nullable = false)
    private Boolean isPrivate;

    @Schema(description = "사용자 프로필 생성일자", example = "20251020T10:00:00")
    @CreatedDate
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Schema(description = "사용자 프로필 수정일자", example = "20251020T10:00:00")
    @LastModifiedDate
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Schema(description = "사용자 프로필 삭제일자", example = "20251020T10:00:00")
    @Setter
    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;


    protected UserProfile() {}

    public UserProfile(String nickname, String profileImage, String introduce, String gender){
//        if (gender == null || gender.isBlank()) throw new IllegalArgumentException("gender is required");

        this.nickname = nickname;
        this.profileImage = profileImage;
        this.introduce = introduce;
        this.gender = gender;

        // default value
        this.isPrivate = false;
        this.createdAt = LocalDateTime.now();
    }

    public static UserProfile create(String nickname, String profileImage, String introduce, String gender){
        return new UserProfile(nickname, profileImage, introduce, gender);
    }

    public void changeNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) throw new IllegalArgumentException("new nickname is required");
        this.nickname = nickname;
    }

    public void changeProfileImage(String profileImage) {
        if (profileImage == null || profileImage.isBlank()) throw new IllegalArgumentException("new profile image is required");
        this.profileImage = profileImage;
    }

    public void changeIntroduce(String introduce) {
        if (introduce == null || introduce.isBlank()) throw new IllegalArgumentException("new introduce is required");
        this.introduce = introduce;
    }

    public void changeGender(String gender) {
        if (gender == null || gender.isBlank()) throw new IllegalArgumentException("new gender value is required");
        this.gender = gender;
    }

    public void changeAccountIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
}
