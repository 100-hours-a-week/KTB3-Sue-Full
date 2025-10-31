package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.*;
import com.example.spring_restapi.dto.response.SignUpResponse;
import com.example.spring_restapi.model.User;

import java.util.List;

public interface UserService {

    User login(LoginRequest req);

    SignUpResponse signup(SignUpRequest req);

    User updateUser(Long user_id, UpdateUserRequest req);

    User removeUser(Long user_id);

    List<User> getAllUsers();

    User getUserById(Long user_id);

    User getUserByEmail(String email);

    User updateUserPassword(Long user_id, UpdatePasswordRequest req);

    User updateUserNickname(Long user_id, UpdateUserNicknameRequest req);

    User updateUserProfileImage(Long user_id, UpdateUserProfileImageRequest req);

    User updateUserIntroduce(Long user_id, UpdateUserIntroduceRequest req);

}
