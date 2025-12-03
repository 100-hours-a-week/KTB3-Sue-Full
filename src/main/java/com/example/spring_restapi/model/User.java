package com.example.spring_restapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@SequenceGenerator(
        name = "user_seq",
        sequenceName = "user_seq",
        allocationSize = 50
)
@Table(name = "user")
public class User extends AbstractAuditable {

    @Schema(description = "사용자 아이디", example = "1L")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Setter
    @Schema(description = "사용자 이메일", example = "2L")
    @Column(name = "email", nullable = false)
    private String email;

    @Setter
    @Schema(description = "사용자 비밀번호", example = "password")
    @Column(name = "password", nullable = false)
    private String password;

    @Transient
    private String passwordConfirm;

    @Setter
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Schema(description = "사용자 생성일자", example = "20251020T10:00:00")
    @CreatedDate
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Schema(description = "사용자 수정일자", example = "20251020T10:00:00")
    @LastModifiedDate
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Schema(description = "사용자 삭제일자", example = "20251020T10:00:00")
    @Setter
    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    public User(){}

    public User(String email, String password, String passwordConfirm, UserRole userRole){
        if (email == null || email.isBlank()) throw new IllegalArgumentException("email is required");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("password is required");
        if (passwordConfirm == null || passwordConfirm.isBlank()) throw new IllegalArgumentException("passwordConfirm is required");

        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;

        this.createdAt = LocalDateTime.now(); // default value

        if(userRole == null) this.userRole = UserRole.USER;
        else this.userRole = userRole;
    }

    public static User create(String email, String password, String passwordConfirm, UserRole userRole) {
        return new User(email, password, passwordConfirm, userRole);
    }

    public void changeEmail(String email) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("email is required");
        this.email = email;
    }

    public void changePassword(String password, String passwordConfirm) {
        if (password == null || password.isBlank()) throw new IllegalArgumentException("password is required");
        if (passwordConfirm == null || passwordConfirm.isBlank()) throw new IllegalArgumentException("password is required");
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public void changeUserRole(UserRole userRole){
        if(userRole == null) throw new IllegalArgumentException("User Role is required");
        this.userRole = userRole;
    }
}
