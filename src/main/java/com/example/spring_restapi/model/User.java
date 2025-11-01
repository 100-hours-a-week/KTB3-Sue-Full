package com.example.spring_restapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User {
    @Schema(description = "사용자 아이디", example = "1L")
    private Long id;

    @Schema(description = "사용자 이메일", example = "2L")
    private String email;

    @Schema(description = "사용자 비밀번호", example = "password")
    private String password;

    protected User(){}

    public User(Long id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }

}
