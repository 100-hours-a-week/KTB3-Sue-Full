package com.example.spring_restapi.controller;

import com.example.spring_restapi.model.User;
import com.example.spring_restapi.dto.request.*;
import com.example.spring_restapi.dto.response.CommonResponse;
import com.example.spring_restapi.dto.response.UserResponse;
import com.example.spring_restapi.dto.response.SignUpResponse;
import com.example.spring_restapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class UserController {
    private final UserService userServiceImpl;

    public UserController(UserService userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 이용하여 로그인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음")
    })
    @PostMapping
    public ResponseEntity<CommonResponse<UserResponse>> login(@RequestBody LoginRequest req){
        User loginUser = userServiceImpl.login(req);

        UserResponse data = new UserResponse(loginUser.getUser_id(), loginUser.getNickname(), loginUser.getProfileImage(), loginUser.getIntroduce());

        CommonResponse<UserResponse> res = CommonResponse.success("login_success", data);
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "회원가입", description = "새로운 유저 정보를 시스템에 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 유저임"),
            @ApiResponse(responseCode = "500", description = "이메일 또는 비밀번호 값을 입력하지 않음")
    })
    @PostMapping("/user")
    public ResponseEntity<CommonResponse<SignUpResponse>> signup(@RequestBody SignUpRequest req){
        SignUpResponse data = userServiceImpl.signup(req);

        CommonResponse<SignUpResponse> res = CommonResponse.success("signup_success", data);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @Operation(summary = "유저 정보 변경", description = "시스템에 등록된 유저의 정보를 변경")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저 정보 변경 성공"),
            @ApiResponse(responseCode = "400", description = "비밀번호가 일치하지 않음"),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음")
    })
    @PatchMapping("/{user_id}")
    public ResponseEntity<CommonResponse<UserResponse>> updateUser(@PathVariable Long user_id, @RequestBody UpdateUserRequest req){
        User updateUser = userServiceImpl.updateUser(user_id, req);

        UserResponse data = new UserResponse(updateUser.getUser_id(), updateUser.getNickname(), updateUser.getProfileImage(), updateUser.getIntroduce());

        CommonResponse<UserResponse> res = CommonResponse.success("profile_edit_success", data);

        return ResponseEntity.ok(res);
    }

    @Operation(summary = "유저 삭제", description = "시스템에 등록된 유저의 정보를 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저 정보 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음")
    })
    @DeleteMapping("/{user_id}")
    public ResponseEntity<CommonResponse<UserResponse>> deleteUser(@PathVariable Long user_id){

        User removeUser = userServiceImpl.removeUser(user_id);

        CommonResponse<UserResponse> res = CommonResponse.success("delete_user_success", null);

        return ResponseEntity.ok(res);
    }

    @Operation(summary = "유저 정보 조회", description = "유저의 아이디를 이용하여 유저 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저 정보 조회 성공"),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음")
    })
    @GetMapping("/{user_id}")
    public ResponseEntity<CommonResponse<User>> findUserById(@PathVariable Long user_id){
        User findUser = userServiceImpl.getUserById(user_id);

        CommonResponse<User> res = new CommonResponse<>("read_userinfo_success", findUser, null);

        return ResponseEntity.ok(res);
    }

    @Operation(summary = "유저 비밀번호 변경", description = "시스템에 등록된 유저의 비밀번호를 변경")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저 비밀번호 변경 성공"),
            @ApiResponse(responseCode = "400", description = "비밀번호가 일치하지 않음")
    })
    @PutMapping("/{user_id}/password")
    public ResponseEntity<CommonResponse<UserResponse>>  updateUserPassword(@PathVariable Long user_id, @RequestBody UpdatePasswordRequest req){
        User updateUser = userServiceImpl.updateUserPassword(user_id, req);

        UserResponse data = new UserResponse(updateUser.getUser_id(), updateUser.getNickname(), updateUser.getProfileImage(), updateUser.getIntroduce());
        CommonResponse<UserResponse> res = CommonResponse.success("update_password_success", data);

        return ResponseEntity.ok(res);
    }

    @Operation(summary = "유저 닉네임 변경", description = "시스템에 등록된 유저의 닉네임을 변경")
    @ApiResponse(responseCode = "200", description = "유저 닉네임 변경 성공")
    @PutMapping("/{user_id}/nickname")
    public ResponseEntity<CommonResponse<UserResponse>> updateUserNickname(@PathVariable Long user_id, @RequestBody UpdateUserNicknameRequest req){
        User updateUser = userServiceImpl.updateUserNickname(user_id, req);

        UserResponse data = new UserResponse(updateUser.getUser_id(), updateUser.getNickname(), updateUser.getProfileImage(), updateUser.getIntroduce());
        CommonResponse<UserResponse> res = CommonResponse.success("update_nickname_success", data);
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "유저 프로필 이미지 변경", description = "시스템에 등록된 유저의 프로필 이미지를 변경")
    @ApiResponse(responseCode = "200", description = "유저 프로필 이미지 변경 성공")
    @PutMapping("/{user_id}/profile_image")
    public ResponseEntity<CommonResponse<UserResponse>> updateUserProfileImage(@PathVariable Long user_id, @RequestBody UpdateUserProfileImageRequest req){
        User updateUser = userServiceImpl.updateUserProfileImage(user_id, req);

        UserResponse data = new UserResponse(updateUser.getUser_id(), updateUser.getNickname(), updateUser.getProfileImage(), updateUser.getIntroduce());
        CommonResponse<UserResponse> res = CommonResponse.success("update_profile_image_success", data);
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "유저 소개말 변경", description = "시스템에 등록된 유저의 소개말 변경")
    @ApiResponse(responseCode = "200", description = "유저 소개말 변경 성공")
    @PutMapping("/{user_id}/introduce")
    public ResponseEntity<CommonResponse<UserResponse>> updateUserIntroduce(@PathVariable Long user_id, @RequestBody UpdateUserIntroduceRequest req){
        User updateUser = userServiceImpl.updateUserIntroduce(user_id, req);

        UserResponse data = new UserResponse(updateUser.getUser_id(), updateUser.getNickname(), updateUser.getProfileImage(), updateUser.getIntroduce());
        CommonResponse<UserResponse> res = CommonResponse.success("update_profile_introduce_success", data);
        return ResponseEntity.ok(res);
    }

}
