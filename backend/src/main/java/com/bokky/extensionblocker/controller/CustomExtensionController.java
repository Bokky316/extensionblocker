package com.bokky.extensionblocker.controller;

import com.bokky.extensionblocker.dto.CustomExtensionRequest;
import com.bokky.extensionblocker.dto.CustomExtensionResponse;
import com.bokky.extensionblocker.service.CustomExtensionService;
import com.bokky.extensionblocker.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/extensions/custom")
@RequiredArgsConstructor
@Tag(name = "커스텀 확장자", description = "커스텀 확장자 등록/조회/삭제 API")
public class CustomExtensionController {

    private final CustomExtensionService customExtensionService;

    @Operation(summary = "커스텀 확장자 등록")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "등록 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력값 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "중복된 확장자"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<CustomExtensionResponse>> addCustomExtension(
            @Valid @RequestBody CustomExtensionRequest request
    ) {
        CustomExtensionResponse response = customExtensionService.addCustomExtension(request);
        return ResponseEntity.ok(ApiResponse.success("등록 성공", response));
    }

    @Operation(summary = "커스텀 확장자 전체 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomExtensionResponse>>> getAllCustomExtensions() {
        List<CustomExtensionResponse> list = customExtensionService.getAllCustomExtensions();
        return ResponseEntity.ok(ApiResponse.success("조회 성공", list));
    }

    @Operation(summary = "커스텀 확장자 삭제")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "해당 ID 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomExtension(@PathVariable Long id) {
        customExtensionService.deleteCustomExtension(id);
        return ResponseEntity.ok(ApiResponse.success("삭제 성공"));
    }
}
