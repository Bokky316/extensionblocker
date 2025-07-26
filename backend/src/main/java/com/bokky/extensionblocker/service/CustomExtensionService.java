package com.bokky.extensionblocker.service;

import com.bokky.extensionblocker.dto.CustomExtensionRequest;
import com.bokky.extensionblocker.entity.CustomExtension;
import com.bokky.extensionblocker.exception.DuplicateExtensionException;
import com.bokky.extensionblocker.exception.MaxExtensionLimitException;
import com.bokky.extensionblocker.repository.CustomExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomExtensionService {

    private final CustomExtensionRepository customExtensionRepository;

    private static final int MAX_CUSTOM_EXTENSION_COUNT = 200;

    @Transactional
    public CustomExtension addCustomExtension(CustomExtensionRequest request) {
        String name = request.getName().trim().toLowerCase();

        // 최대 개수 제한
        if (customExtensionRepository.count() >= MAX_CUSTOM_EXTENSION_COUNT) {
            throw new MaxExtensionLimitException("커스텀 확장자는 최대 200개까지 등록할 수 있습니다.");
        }

        // 중복 체크
        if (customExtensionRepository.findByName(name).isPresent()) {
            throw new DuplicateExtensionException("이미 존재하는 확장자입니다: " + name);
        }

        CustomExtension extension = CustomExtension.builder()
                .name(name)
                .build();

        return customExtensionRepository.save(extension);
    }

    @Transactional(readOnly = true)
    public List<CustomExtension> getAllCustomExtensions() {
        return customExtensionRepository.findAll();
    }

    @Transactional
    public void deleteCustomExtension(Long id) {
        customExtensionRepository.deleteById(id);
    }
}
