package com.example.spring_restapi.error;

import com.example.spring_restapi.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CommonResponse<Object>> handleResponseStatusException(ResponseStatusException ex) {

        // ex.getStatusCode() 안에 404 / 400 이런 상태 코드 들어있음
        var status = ex.getStatusCode();
        var reason = ex.getReason();   // "not_found" 같은 문자열

        CommonResponse<Object> body =
                CommonResponse.error(reason, null);  // message = "not_found"

        return ResponseEntity
                .status(status) // 여기서 404 그대로 사용
                .body(body);
    }

    // 나머지 알 수 없는 에러는 500으로
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Object>> handleGeneric(Exception ex) {
        ex.printStackTrace(); // 로그 남기고
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.error("internal_server_error", ex.getMessage()));
    }
}
