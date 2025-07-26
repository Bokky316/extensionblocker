package com.bokky.extensionblocker.dto;

import com.bokky.extensionblocker.entity.CustomExtension;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomExtensionResponse {
    private Long id;
    private String name;

    public static CustomExtensionResponse fromEntity(CustomExtension entity) {
        return CustomExtensionResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
