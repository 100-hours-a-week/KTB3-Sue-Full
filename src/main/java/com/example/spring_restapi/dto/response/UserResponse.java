package com.example.spring_restapi.dto.response;

public class UserResponse {
    private Long user_id;
    private String nickname;
    private String profile_image;
    private String introduce;

    public UserResponse(Long user_id, String nickname, String profile_image, String introduce){
        this.user_id = user_id;
        this.nickname = nickname;
        this.profile_image = profile_image;
        this.introduce = introduce;
    }

    public Long getUser_id() { return user_id; }
    public String getNickname() { return nickname; }
    public String getProfile_image() { return profile_image; }
    public String getIntroduce() { return introduce; }
}
