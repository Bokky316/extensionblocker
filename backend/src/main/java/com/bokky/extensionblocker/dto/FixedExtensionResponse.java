package com.bokky.extensionblocker.dto;

import com.bokky.extensionblocker.entity.FixedExtension;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FixedExtensionResponse {
    private Long id;
    private String name;
    private boolean checked;

    public static FixedExtensionResponse from(FixedExtension entity) {
        return new FixedExtensionResponse(
                entity.getId(),
                entity.getName().name().toLowerCase(),
                entity.isChecked()
        );
    }
}
