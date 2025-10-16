package com.example.spring_restapi.controller;

import com.example.spring_restapi.dto.User;
import com.example.spring_restapi.dto.request.*;
import com.example.spring_restapi.dto.response.CommonResponse;
import com.example.spring_restapi.dto.response.UserResponse;
import com.example.spring_restapi.dto.response.SignUpResponse;
import com.example.spring_restapi.dto.response.UpdateUserResponse;
import com.example.spring_restapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<UserResponse>> login(@RequestBody LoginRequest req){
        User loginUser = userService.login(req);

        UserResponse data = new UserResponse(loginUser.getUser_id(), loginUser.getNickname(), loginUser.getProfileImage(), loginUser.getIntroduce());

        CommonResponse<UserResponse> res = CommonResponse.success("login_success", data);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/user")
    public ResponseEntity<CommonResponse<SignUpResponse>> signup(@RequestBody SignUpRequest req){
        SignUpResponse data = userService.signup(req);

        CommonResponse<SignUpResponse> res = CommonResponse.success("signup_success", data);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PatchMapping("/{user_id}")
    public ResponseEntity<CommonResponse<UserResponse>> updateUser(@PathVariable Long user_id, @RequestBody UpdateUserRequest req){
        User updateUser = userService.updateUser(user_id, req);

        UserResponse data = new UserResponse(updateUser.getUser_id(), updateUser.getNickname(), updateUser.getProfileImage(), updateUser.getIntroduce());

        CommonResponse<UserResponse> res = CommonResponse.success("profile_edit_success", data);

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<CommonResponse<UserResponse>> deleteUSer(@PathVariable Long user_id){

        User removeUser = userService.removeUser(user_id);

        CommonResponse<UserResponse> res = CommonResponse.success("delete_user_success", null);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<CommonResponse<User>> findUserById(@PathVariable Long user_id){
        User findUser = userService.getUserById(user_id);

        CommonResponse<User> res = new CommonResponse<>("read_userinfo_success", findUser, null);

        return ResponseEntity.ok(res);
    }

    @PutMapping("/{user_id}/password")
    public ResponseEntity<CommonResponse<UserResponse>>  updateUserPassword(@PathVariable Long user_id, @RequestBody UpdatePasswordRequest req){
        User updateUser = userService.updateUserPassword(user_id, req);

        UserResponse data = new UserResponse(updateUser.getUser_id(), updateUser.getNickname(), updateUser.getProfileImage(), updateUser.getIntroduce());
        CommonResponse<UserResponse> res = CommonResponse.success("update_password_success", data);

        return ResponseEntity.ok(res);
    }

    @PutMapping("/{user_id}/nickname")
    public ResponseEntity<CommonResponse<UserResponse>> updateUserNickname(@PathVariable Long user_id, @RequestBody UpdateUserNicknameRequest req){
        User updateUser = userService.updateUserNickname(user_id, req);

        UserResponse data = new UserResponse(updateUser.getUser_id(), updateUser.getNickname(), updateUser.getProfileImage(), updateUser.getIntroduce());
        CommonResponse<UserResponse> res = CommonResponse.success("update_nickname_success", data);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{user_id}/profile_image")
    public ResponseEntity<CommonResponse<UserResponse>> updateUserProfileImage(@PathVariable Long user_id, @RequestBody UpdateUserProfileImageRequest req){
        User updateUser = userService.updateUserProfileImage(user_id, req);

        UserResponse data = new UserResponse(updateUser.getUser_id(), updateUser.getNickname(), updateUser.getProfileImage(), updateUser.getIntroduce());
        CommonResponse<UserResponse> res = CommonResponse.success("update_profile_image_success", data);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{user_id}/introduce")
    public ResponseEntity<CommonResponse<UserResponse>> updateUserIntroduce(@PathVariable Long user_id, @RequestBody UpdateUserIntroduceRequest req){
        User updateUser = userService.updateUserIntroduce(user_id, req);

        UserResponse data = new UserResponse(updateUser.getUser_id(), updateUser.getNickname(), updateUser.getProfileImage(), updateUser.getIntroduce());
        CommonResponse<UserResponse> res = CommonResponse.success("update_profile_introduce_success", data);
        return ResponseEntity.ok(res);
    }

}
