package com.example.spring_restapi.dto.response;

import com.example.spring_restapi.model.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserInfoResponse {
    @Schema(description = "유저 정보 응답 - 유저 아이디", example = "1L")
    private Long user_id;

    @Schema(description = "유저 정보 응답 - 유저 이메일", example = "user@email.com")
    private String email;

    @Schema(description = "유저 정보 응답 - 유저 역할", example = "USER or ADMIN")
    private UserRole userRole;

    public UserInfoResponse(Long user_id, String email, UserRole userRole){
        this.user_id = user_id;
        this.email = email;
        this.userRole = userRole;
    }
}
