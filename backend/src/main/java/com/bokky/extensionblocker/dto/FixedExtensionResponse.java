package com.bokky.extensionblocker.dto;

import com.bokky.extensionblocker.entity.FixedExtension;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FixedExtensionResponse {
    private Long id;
    private String name;
    private boolean checked;

    public static FixedExtensionResponse from(FixedExtension entity) {
        return FixedExtensionResponse.builder()
                .id(entity.getId())
                .name(entity.getName().name().toLowerCase())
                .checked(entity.isChecked())
                .build();
    }
}
