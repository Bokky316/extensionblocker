package com.bokky.extensionblocker.controller;

import com.bokky.extensionblocker.common.response.ApiResponse;
import com.bokky.extensionblocker.dto.FixedExtensionResponse;
import com.bokky.extensionblocker.service.FixedExtensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fixed")
@RequiredArgsConstructor
public class FixedExtensionController {

    private final FixedExtensionService fixedExtensionService;

    /**
     * 고정 확장자 전체 조회 API
     * - checked 여부 포함한 전체 목록 반환
     */
    @GetMapping
    public ApiResponse<List<FixedExtensionResponse>> getAllFixedExtensions() {
        List<FixedExtensionResponse> fixedExtensions = fixedExtensionService.getAllFixedExtensions();
        return ApiResponse.success("성공", fixedExtensions);
    }

    /**
     * 고정 확장자 상태 토글 API
     * - 체크 상태를 반전 (checked ↔ unchecked)
     */
    @PutMapping("/{id}")
    public ApiResponse<String> toggleCheckedStatus(@PathVariable Long id) {
        fixedExtensionService.toggleCheckedStatus(id);
        return ApiResponse.success("상태 변경 성공");
    }
}
