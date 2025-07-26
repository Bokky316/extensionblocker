package com.bokky.extensionblocker.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "공통 응답 구조")
public class ApiResponse<T> {

    @Schema(description = "응답 코드", example = "200")
    private int code;

    @Schema(description = "응답 메시지", example = "요청 성공")
    private String message;

    @Schema(description = "응답 데이터")
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
