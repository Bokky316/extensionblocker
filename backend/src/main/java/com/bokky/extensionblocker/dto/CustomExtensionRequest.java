package com.bokky.extensionblocker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "커스텀 확장자 등록 요청 DTO")
public class CustomExtensionRequest {

    @Schema(description = "추가할 확장자명", example = "sh", maxLength = 20, nullable = false)
    @NotBlank(message = "확장자명은 공백일 수 없습니다.")
    @Size(max = 20, message = "확장자명은 최대 20자까지 허용됩니다.")
    private String name;
}
