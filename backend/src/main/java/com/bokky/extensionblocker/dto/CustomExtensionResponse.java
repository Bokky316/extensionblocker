package com.bokky.extensionblocker.dto;

import com.bokky.extensionblocker.entity.CustomExtension;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Schema(description = "커스텀 확장자 응답 DTO")
public class CustomExtensionResponse {

    @Schema(description = "확장자 ID", example = "12")
    private Long id;

    @Schema(description = "확장자명", example = "sh")
    private String name;

    @Schema(description = "생성일시", example = "2024-07-26T12:34:56")
    private LocalDateTime createdAt;

    public static CustomExtensionResponse fromEntity(CustomExtension entity) {
        return new CustomExtensionResponse(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt()
        );
    }
}
