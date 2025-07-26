package com.bokky.extensionblocker.controller;

import com.bokky.extensionblocker.dto.CustomExtensionRequest;
import com.bokky.extensionblocker.entity.CustomExtension;
import com.bokky.extensionblocker.service.CustomExtensionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/custom")
@RequiredArgsConstructor
public class CustomExtensionController {

    private final CustomExtensionService customExtensionService;

    /**
     * 커스텀 확장자 등록
     */
    @PostMapping
    public ResponseEntity<CustomExtension> addCustomExtension(
            @Valid @RequestBody CustomExtensionRequest request
    ) {
        CustomExtension saved = customExtensionService.addCustomExtension(request);
        return ResponseEntity.ok(saved);
    }

    /**
     * 전체 커스텀 확장자 조회
     */
    @GetMapping
    public ResponseEntity<List<CustomExtension>> getAllCustomExtensions() {
        List<CustomExtension> list = customExtensionService.getAllCustomExtensions();
        return ResponseEntity.ok(list);
    }

    /**
     * 커스텀 확장자 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomExtension(@PathVariable Long id) {
        customExtensionService.deleteCustomExtension(id);
        return ResponseEntity.noContent().build();
    }
}
