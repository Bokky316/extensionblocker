package com.bokky.extensionblocker.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return success("요청 성공", data);
    }

    public static ApiResponse<String> success(String message) {
        return new ApiResponse<>(200, message, null);
    }

    public static ApiResponse<String> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}
