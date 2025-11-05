package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.*;
import com.example.spring_restapi.dto.response.UserInfoResponse;
import com.example.spring_restapi.dto.response.UserProfileResponse;
import com.example.spring_restapi.dto.response.UserResponse;

public interface UserService {

    UserResponse login(LoginRequest req);

    UserResponse signup(SignUpRequest req);

    UserResponse getUserById(Long user_id);

    UserResponse getUserByEmail(String email);

    UserInfoResponse updateUserInfo(Long user_id, UpdateUserInfoRequest req);

    UserInfoResponse updateUserPassword(Long user_id, UpdatePasswordRequest req);

    UserProfileResponse updateUserNickname(Long user_id, UpdateUserNicknameRequest req);

    UserProfileResponse updateUserProfileImage(Long user_id, UpdateUserProfileImageRequest req);

    UserProfileResponse updateUserIntroduce(Long user_id, UpdateUserIntroduceRequest req);

    UserProfileResponse updateUserGender(Long user_id, UpdateUserGenderRequest req);

    UserProfileResponse updateUserProfileIsPrivate(Long user_id, UpdateUserProfileIsPrivateRequest req);

    UserResponse removeUser(Long user_id);

}
