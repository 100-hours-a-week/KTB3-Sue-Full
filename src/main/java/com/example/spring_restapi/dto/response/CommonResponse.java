package com.example.spring_restapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class CommonResponse<T> {
    @Schema(description = "응답 메시지", example = "로그인 성공")
    private String message;

    @Schema(description = "응답 데이터", example = "email: osj1405@naver.com, password: osj1405")
    private T data;

    @Schema(description = "응답 에러", example = "error message")
    private Object error;

    public CommonResponse(String message, T data, Object error){
        this.message = message;
        this.data = data;
        this.error = error;
    }

    public String getMessage() { return message; }
    public T getData() { return data; }
    public Object getError() { return error; }

    public static <T> CommonResponse<T> success(String message, T data) {
        return new CommonResponse<>(message, data, null);
    }

    public static <T> CommonResponse<T> error(String message, Object error) {
        return new CommonResponse<>(message, null, error);
    }

}
