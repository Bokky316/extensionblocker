package com.bokky.extensionblocker.service;

import com.bokky.extensionblocker.dto.CustomExtensionRequest;
import com.bokky.extensionblocker.dto.CustomExtensionResponse;
import com.bokky.extensionblocker.entity.CustomExtension;
import com.bokky.extensionblocker.entity.FixedExtensionType;
import com.bokky.extensionblocker.exception.DuplicateExtensionException;
import com.bokky.extensionblocker.exception.MaxExtensionLimitException;
import com.bokky.extensionblocker.repository.CustomExtensionRepository;
import com.bokky.extensionblocker.repository.FixedExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomExtensionService {

    private static final int MAX_CUSTOM_EXTENSIONS = 200;

    private final CustomExtensionRepository customExtensionRepository;
    private final FixedExtensionRepository fixedExtensionRepository;

    /**
     * 커스텀 확장자 추가
     * - 중복 확인
     * - 개수 제한 확인
     * - 소문자 + trim 처리
     */
    public CustomExtensionResponse addCustomExtension(CustomExtensionRequest request) {
        String name = request.getName().trim().toLowerCase();

        try {
            FixedExtensionType fixedType = FixedExtensionType.valueOf(name.toUpperCase());
            if (fixedExtensionRepository.findByName(fixedType).isPresent()) {
                throw new DuplicateExtensionException("이미 고정 확장자에 존재합니다: " + name);
            }
        } catch (IllegalArgumentException ignored) {
            // 고정 enum에 없는 경우는 무시하고 커스텀 로직 계속 진행
        }

        // 커스텀 확장자 중복 확인
        if (customExtensionRepository.existsByName(name)) {
            throw new DuplicateExtensionException("이미 등록된 커스텀 확장자입니다: " + name);
        }

        // 최대 개수 초과 확인
        if (customExtensionRepository.count() >= MAX_CUSTOM_EXTENSIONS) {
            throw new MaxExtensionLimitException();
        }

        CustomExtension entity = CustomExtension.builder()
                .name(name)
                .build();

        return CustomExtensionResponse.fromEntity(customExtensionRepository.save(entity));
    }

    /**
     * 커스텀 확장자 전체 조회 (생성일 기준 최신순)
     */
    public List<CustomExtensionResponse> getAllCustomExtensions() {
        return customExtensionRepository.findAllByOrderByCreatedAtDesc().stream()
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
