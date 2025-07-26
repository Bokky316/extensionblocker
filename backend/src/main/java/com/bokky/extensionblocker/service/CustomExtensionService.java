package com.bokky.extensionblocker.service;

import com.bokky.extensionblocker.dto.CustomExtensionRequest;
import com.bokky.extensionblocker.dto.CustomExtensionResponse;
import com.bokky.extensionblocker.entity.CustomExtension;
import com.bokky.extensionblocker.exception.DuplicateExtensionException;
import com.bokky.extensionblocker.exception.MaxExtensionLimitException;
import com.bokky.extensionblocker.repository.CustomExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomExtensionService {

    private static final int MAX_CUSTOM_EXTENSIONS = 200;

    private final CustomExtensionRepository customExtensionRepository;

    /**
     * 커스텀 확장자 추가
     * - 중복 확인
     * - 개수 제한 확인
     * - 소문자 + trim 처리
     */
    public CustomExtensionResponse addCustomExtension(CustomExtensionRequest request) {
        String name = request.getName().trim().toLowerCase();

        if (customExtensionRepository.existsByName(name)) {
            throw new DuplicateExtensionException(name);
        }

        if (customExtensionRepository.count() >= MAX_CUSTOM_EXTENSIONS) {
            throw new MaxExtensionLimitException();
        }

        CustomExtension entity = CustomExtension.builder()
                .name(name)
                .build();

        return CustomExtensionResponse.fromEntity(customExtensionRepository.save(entity));
    }

    /**
     * 커스텀 확장자 전체 조회 (최신순)
     */
    public List<CustomExtensionResponse> getAllCustomExtensions() {
        return customExtensionRepository.findAll().stream()
                .map(CustomExtensionResponse::fromEntity)
                .toList();
    }

    /**
     * 커스텀 확장자 삭제
     */
    public void deleteCustomExtension(Long id) {
        customExtensionRepository.deleteById(id);
    }
}
