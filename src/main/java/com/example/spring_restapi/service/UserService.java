package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.*;
import com.example.spring_restapi.dto.response.UserInfoResponse;
import com.example.spring_restapi.dto.response.UserProfileResponse;
import com.example.spring_restapi.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.io.IOException;
import java.util.List;

public interface UserService {

    UserResponse login(LoginRequest req);

    UserResponse signup(SignUpRequest req) throws IOException;

    UserResponse getUserById(Long user_id);

    UserResponse getUserByEmail(String email);

    UserInfoResponse updateUserInfo(Long user_id, UpdateUserInfoRequest req);

    UserInfoResponse updateUserPassword(Long user_id, UpdatePasswordRequest req);

    UserProfileResponse updateUserNickname(Long user_id, UpdateUserNicknameRequest req);

    UserProfileResponse updateUserProfileImage(Long user_id, UpdateUserProfileImageRequest req);

    UserProfileResponse updateUserIntroduce(Long user_id, UpdateUserIntroduceRequest req);

    UserProfileResponse updateUserGender(Long user_id, UpdateUserGenderRequest req);

    UserProfileResponse updateUserProfileIsPrivate(Long user_id, UpdateUserProfileIsPrivateRequest req);

    UserProfileResponse updateUserNicknameAndProfileImage(UpdateUserNicknameProfileImageReqeust req) throws IOException;

    UserResponse removeUser(Long user_id);

    List<UserProfileResponse> searchAsList(String keyword);

    Page<UserProfileResponse> searchAsPage(String keyword, int page, int size, String sortBy, String direction);

    Slice<UserProfileResponse> searchAsSlice(String keyword, int page, int size, String sortBy, String direction);

    Boolean checkEmailConflict(EmailCheckRequest req);

    Boolean checkNicknameConflict(NicknameCheckRequest req);
}
