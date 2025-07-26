package com.bokky.extensionblocker.service;

import com.bokky.extensionblocker.dto.FixedExtensionResponse;
import com.bokky.extensionblocker.entity.FixedExtension;
import com.bokky.extensionblocker.entity.FixedExtensionType;
import com.bokky.extensionblocker.repository.FixedExtensionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * [단위 테스트] FixedExtensionService
 * - 고정 확장자 전체 조회
 * - checked 상태 토글
 */
class FixedExtensionServiceTest {

    private FixedExtensionRepository fixedExtensionRepository;
    private FixedExtensionService fixedExtensionService;

    @BeforeEach
    void setUp() {
        fixedExtensionRepository = mock(FixedExtensionRepository.class);
        fixedExtensionService = new FixedExtensionService(fixedExtensionRepository);
    }

    @Test
    @DisplayName("고정 확장자 전체 조회 시 DTO 리스트로 변환되어 반환되어야 한다")
    void getAllFixedExtensions_shouldReturnList() {
        // given
        List<FixedExtension> mockEntities = Arrays.asList(
                FixedExtension.builder().id(1L).name(FixedExtensionType.EXE).checked(true).build(),
                FixedExtension.builder().id(2L).name(FixedExtensionType.SH).checked(false).build()
        );
        when(fixedExtensionRepository.findAll()).thenReturn(mockEntities);

        // when
        List<FixedExtensionResponse> result = fixedExtensionService.getAllFixedExtensions();

        // then
        assertEquals(2, result.size());
        assertEquals("exe", result.get(0).getName());
        assertTrue(result.get(0).isChecked());
        assertEquals("sh", result.get(1).getName());
        assertFalse(result.get(1).isChecked());
    }

    @Test
    @DisplayName("checked 상태가 true일 경우 toggle 호출 시 false가 되어야 한다")
    void toggleChecked_shouldFlipTrueToFalse() {
        // given
        FixedExtension extension = FixedExtension.builder()
                .id(1L)
                .name(FixedExtensionType.EXE)
                .checked(true)
                .build();
        when(fixedExtensionRepository.findById(1L)).thenReturn(Optional.of(extension));

        // when
        fixedExtensionService.toggleCheckedStatus(1L);

        // then
        assertFalse(extension.isChecked());
        verify(fixedExtensionRepository).save(extension);
    }

    @Test
    @DisplayName("checked 상태가 false일 경우 toggle 호출 시 true가 되어야 한다")
    void toggleChecked_shouldFlipFalseToTrue() {
        // given
        FixedExtension extension = FixedExtension.builder()
                .id(2L)
                .name(FixedExtensionType.SH)
                .checked(false)
                .build();
        when(fixedExtensionRepository.findById(2L)).thenReturn(Optional.of(extension));

        // when
        fixedExtensionService.toggleCheckedStatus(2L);

        // then
        assertTrue(extension.isChecked());
        verify(fixedExtensionRepository).save(extension);
    }

    @Test
    @DisplayName("존재하지 않는 확장자 ID로 toggle 시 IllegalArgumentException이 발생해야 한다")
    void toggleChecked_shouldThrowExceptionIfNotFound() {
        // given
        when(fixedExtensionRepository.findById(999L)).thenReturn(Optional.empty());

        // when & then
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> fixedExtensionService.toggleCheckedStatus(999L));

        // 메시지 검증 추가
        assertEquals("해당 확장자를 찾을 수 없습니다.", ex.getMessage());
    }
}
