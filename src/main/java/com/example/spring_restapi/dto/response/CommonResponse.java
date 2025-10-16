package com.example.spring_restapi.dto.response;

public class CommonResponse<T> {
    private String message;
    private T data;
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
