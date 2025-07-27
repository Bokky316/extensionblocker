// src/main/java/com/bokky/extensionblocker/exception/GlobalExceptionHandler.java

package com.bokky.extensionblocker.exception;

import com.bokky.extensionblocker.common.response.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GlobalExceptionHandler {

    /**
     * @Valid 유효성 실패 처리
     * - code: 4000
     * - data: { 필드명: 메시지 }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity
                .badRequest()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(ApiResponse.error(4000, "유효성 검사 실패", errors));
    }

    /**
     * 커스텀 확장자 중복 에러
     * - code: 4001
     */
    @ExceptionHandler(DuplicateExtensionException.class)
    public ResponseEntity<ApiResponse<String>> handleDuplicate(DuplicateExtensionException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(ApiResponse.error(4001, "중복된 확장자", null));
    }

    /**
     * 최대 200개 초과 에러
     * - code: 4002
     */
    @ExceptionHandler(MaxExtensionLimitException.class)
    public ResponseEntity<ApiResponse<String>> handleMaxLimit(MaxExtensionLimitException e) {
        return ResponseEntity
                .badRequest()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(ApiResponse.error(4002, "확장자 개수 초과", null));
    }

    /**
     * 기타 예상 못한 에러
     * - code: 5000
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleOther(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(ApiResponse.error(5000, "서버 오류", null));
    }
}
