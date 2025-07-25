package com.bokky.extensionblocker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 커스텀 확장자 추가 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomExtensionRequest {

    @NotBlank(message = "확장자명은 공백일 수 없습니다.")
    @Size(max = 20, message = "확장자명은 최대 20자까지 허용됩니다.")
    private String name;
}
