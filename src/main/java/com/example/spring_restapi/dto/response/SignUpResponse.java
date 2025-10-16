package com.example.spring_restapi.dto.response;

import java.util.List;

public class SignUpResponse {
    private Long user_id;
    private String email;
    private String nickname;
    private String profile_image;
    private String introduce;

    public SignUpResponse(Long user_id, String email, String nickname, String profile_image, String introduce){
        this.user_id = user_id;
        this.email = email;
        this.nickname = nickname;
        this.profile_image = profile_image;
        this.introduce = introduce;
    }

    public Long getUser_id() { return user_id; }
    public String getEmail() { return email; }
    public String getNickname() { return nickname; }
    public String getProfile_image() { return profile_image; }
    public String getIntroduce() { return introduce; }
}
