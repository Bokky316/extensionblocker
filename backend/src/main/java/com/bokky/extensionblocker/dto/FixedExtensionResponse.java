package com.bokky.extensionblocker.dto;

import com.bokky.extensionblocker.entity.FixedExtension;
import com.bokky.extensionblocker.entity.FixedExtensionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Schema(description = "고정 확장자 응답 DTO")
public class FixedExtensionResponse {

    @Schema(description = "고정 확장자 ID", example = "1")
    private Long id;

    @Schema(description = "고정 확장자명", example = "EXE")
    private FixedExtensionType name;

    @Schema(description = "차단 여부", example = "true")
    private boolean checked;

    public static FixedExtensionResponse from(FixedExtension entity) {
        return new FixedExtensionResponse(
                entity.getId(),
                entity.getName(),
                entity.isChecked()
        );
    }
}
