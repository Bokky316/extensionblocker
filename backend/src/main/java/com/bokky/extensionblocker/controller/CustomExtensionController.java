package com.bokky.extensionblocker.controller;

import com.bokky.extensionblocker.dto.CustomExtensionRequest;
import com.bokky.extensionblocker.dto.CustomExtensionResponse;
import com.bokky.extensionblocker.service.CustomExtensionService;
import com.bokky.extensionblocker.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 커스텀 확장자 API 컨트롤러
 */
@RestController
@RequestMapping("/api/extensions/custom")
@RequiredArgsConstructor
public class CustomExtensionController {

    private final CustomExtensionService customExtensionService;

    /**
     * 커스텀 확장자 등록
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CustomExtensionResponse>> addCustomExtension(
            @Valid @RequestBody CustomExtensionRequest request
    ) {
        CustomExtensionResponse response = customExtensionService.addCustomExtension(request);
        return ResponseEntity.ok(ApiResponse.success("등록 성공", response));
    }

    /**
     * 커스텀 확장자 전체 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomExtensionResponse>>> getAllCustomExtensions() {
        List<CustomExtensionResponse> list = customExtensionService.getAllCustomExtensions();
        return ResponseEntity.ok(ApiResponse.success("조회 성공", list));
    }

    /**
     * 커스텀 확장자 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomExtension(@PathVariable Long id) {
        customExtensionService.deleteCustomExtension(id);
        return ResponseEntity.ok(ApiResponse.success("삭제 성공"));
    }
}
