package com.bokky.extensionblocker.controller;

import com.bokky.extensionblocker.dto.FixedExtensionResponse;
import com.bokky.extensionblocker.service.FixedExtensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/extensions")
@RequiredArgsConstructor
public class FixedExtensionController {

    private final FixedExtensionService fixedExtensionService;

    /**
     * [GET] /api/extensions/fixed
     * - 고정 확장자 목록 조회
     */
    @GetMapping("/fixed")
    public ResponseEntity<List<FixedExtensionResponse>> getFixedExtensions() {
        return ResponseEntity.ok(fixedExtensionService.getAllFixedExtensions());
    }

    /**
     * [PUT] /api/extensions/fixed/{id}?checked=true
     * - 고정 확장자 체크 상태 변경
     */
    @PutMapping("/fixed/{id}")
    public ResponseEntity<Void> updateChecked(
            @PathVariable Long id,
            @RequestParam boolean checked) {
        fixedExtensionService.updateCheckedStatus(id, checked);
        return ResponseEntity.ok().build();
    }
}
