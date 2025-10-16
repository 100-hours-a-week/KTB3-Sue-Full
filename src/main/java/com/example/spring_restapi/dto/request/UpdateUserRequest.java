package com.example.spring_restapi.dto.request;

public class UpdateUserRequest {
    private String currentPassword;
    private String newPassword;
    private String nickname;
    private String profile_image;
    private String introduce;

    public String getCurrentPassword(){ return currentPassword; }

    public String getNewPassword(){ return newPassword; }

    public String getNickname(){ return nickname; }

    public String getProfile_image() { return profile_image; }

    public String getIntroduce(){ return introduce; }
}
