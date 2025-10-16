package com.example.spring_restapi.dto.request;

public class UpdatePasswordRequest {
    private String currentPassword;
    private String newPassword;

    public String getCurrentPassword() { return currentPassword; }

    public String getNewPassword() { return newPassword; }
}
