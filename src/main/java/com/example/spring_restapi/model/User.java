package com.example.spring_restapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class User {
    @Schema(description = "사용자 아이디", example = "1L")
    private Long user_id;

    @Schema(description = "사용자 이메일", example = "2L")
    private String email;

    @Schema(description = "사용자 비밀번호", example = "password")
    private String password;

    @Schema(description = "사용자 닉네임", example = "sue")
    private String nickname;

    @Schema(description = "사용자 프로필 이미지", example = "profileImage.jpg")
    private String profileImage;

    @Schema(description = "사용자 소개말", example = "Hi, I'm sue")
    private String introduce;

    @Schema(description = "사용자 토큰", example = "eJ...")
    private String token;

    public User(){}

    public User(Long user_id, String email, String password, String nickname, String profileImage, String introduce, String token){
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.introduce = introduce;
        this.token = token;
    }

    // Setter
    public void setUser_id(Long user_id){
        this.user_id = user_id;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void setProfileImage(String profileImage){
        this.profileImage = profileImage;
    }

    public void setIntroduce(String introduce){
        this.introduce = introduce;
    }

    public void setToken(String token){
        this.token = token;
    }

    // Getter
    public Long getUser_id(){
        return user_id;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getNickname(){
        return nickname;
    }

    public String getProfileImage(){
        return profileImage;
    }

    public String getIntroduce(){
        return introduce;
    }

    public String getToken(){
        return token;
    }
}
