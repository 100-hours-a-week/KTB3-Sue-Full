package com.example.spring_restapi.dto;

import java.util.Optional;

public class User {
    private Long user_id;
    private String email;
    private String password;
    private String nickname;
    private String profileImage;
    private String introduce;
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
