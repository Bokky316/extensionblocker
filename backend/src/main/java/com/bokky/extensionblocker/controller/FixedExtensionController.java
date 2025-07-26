package com.bokky.extensionblocker.controller;

import com.bokky.extensionblocker.common.response.ApiResponse;
import com.bokky.extensionblocker.dto.FixedExtensionResponse;
import com.bokky.extensionblocker.service.FixedExtensionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fixed")
@RequiredArgsConstructor
@Tag(name = "고정 확장자", description = "고정 확장자 조회 및 체크 상태 변경 API")
public class FixedExtensionController {

    private final FixedExtensionService fixedExtensionService;

    @Operation(summary = "고정 확장자 전체 조회", description = "고정 확장자 목록을 checked 상태 포함하여 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping
    public ApiResponse<List<FixedExtensionResponse>> getAllFixedExtensions() {
        List<FixedExtensionResponse> fixedExtensions = fixedExtensionService.getAllFixedExtensions();
        return ApiResponse.success("성공", fixedExtensions);
    }

    @Operation(summary = "고정 확장자 상태 토글", description = "지정한 확장자의 checked 상태를 반전시킵니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "상태 변경 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 ID"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PutMapping("/{id}")
    public ApiResponse<String> toggleCheckedStatus(@PathVariable Long id) {
        fixedExtensionService.toggleCheckedStatus(id);
        return ApiResponse.success("상태 변경 성공");
    }
}
