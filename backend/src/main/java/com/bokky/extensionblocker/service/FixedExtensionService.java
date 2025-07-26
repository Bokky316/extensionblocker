package com.bokky.extensionblocker.service;

import com.bokky.extensionblocker.dto.FixedExtensionResponse;
import com.bokky.extensionblocker.entity.FixedExtension;
import com.bokky.extensionblocker.repository.FixedExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 고정 확장자 관련 서비스
 * - 전체 목록 조회
 * - 체크 상태 수정
 */
@Service
@RequiredArgsConstructor
public class FixedExtensionService {

    private final FixedExtensionRepository fixedExtensionRepository;

    /**
     * 고정 확장자 전체 조회
     * - Entity → DTO(FixedExtensionResponse) 변환하여 반환
     */
    public List<FixedExtensionResponse> getAllFixedExtensions() {
        return fixedExtensionRepository.findAll().stream()
                .map(FixedExtensionResponse::from)
                .toList();
    }

    /**
     * 체크 상태 변경
     * @param id 고정 확장자 ID
     * @param checked true/false 상태
     */
    public void updateCheckedStatus(Long id, boolean checked) {
        FixedExtension extension = fixedExtensionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 확장자를 찾을 수 없습니다: " + id));
        extension.updateChecked(checked);
        fixedExtensionRepository.save(extension);
    }
}
