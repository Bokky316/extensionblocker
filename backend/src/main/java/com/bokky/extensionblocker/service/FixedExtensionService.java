package com.bokky.extensionblocker.service;

import com.bokky.extensionblocker.dto.FixedExtensionResponse;
import com.bokky.extensionblocker.entity.FixedExtension;
import com.bokky.extensionblocker.repository.FixedExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FixedExtensionService {

    private final FixedExtensionRepository fixedExtensionRepository;

    public List<FixedExtensionResponse> getAllFixedExtensions() {
        return fixedExtensionRepository.findAll().stream()
                .map(FixedExtensionResponse::from)
                .toList();
    }

    public void toggleCheckedStatus(Long id) {
        FixedExtension extension = fixedExtensionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 확장자를 찾을 수 없습니다."));
        extension.toggleChecked();
        fixedExtensionRepository.save(extension);
    }
}
