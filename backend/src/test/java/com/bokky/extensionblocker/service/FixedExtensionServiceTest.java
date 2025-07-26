package com.bokky.extensionblocker.service;

import com.bokky.extensionblocker.dto.FixedExtensionResponse;
import com.bokky.extensionblocker.entity.FixedExtension;
import com.bokky.extensionblocker.entity.FixedExtensionType;
import com.bokky.extensionblocker.repository.FixedExtensionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * FixedExtensionService 단위 테스트
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
    void 고정확장자_전체조회() {
        // given
        List<FixedExtension> mockList = Arrays.asList(
                FixedExtension.builder().id(1L).name(FixedExtensionType.EXE).checked(true).build(),
                FixedExtension.builder().id(2L).name(FixedExtensionType.BAT).checked(false).build()
        );
        when(fixedExtensionRepository.findAll()).thenReturn(mockList);

        // when
        List<FixedExtensionResponse> result = fixedExtensionService.getAllFixedExtensions();

        // then
        assertEquals(2, result.size());
        assertEquals("exe", result.get(0).getName());
        assertTrue(result.get(0).isChecked());
        assertEquals("bat", result.get(1).getName());
        assertFalse(result.get(1).isChecked());
    }

    @Test
    void 고정확장자_체크상태_업데이트_정상() {
        // given
        FixedExtension fixedExtension = FixedExtension.builder()
                .id(1L)
                .name(FixedExtensionType.SCR)
                .checked(false)
                .build();

        when(fixedExtensionRepository.findById(1L)).thenReturn(Optional.of(fixedExtension));

        // when
        fixedExtensionService.updateCheckedStatus(1L, true);

        // then
        assertTrue(fixedExtension.isChecked());
        verify(fixedExtensionRepository).save(fixedExtension);
    }

    @Test
    void 고정확장자_체크상태_업데이트_예외() {
        // given
        when(fixedExtensionRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () ->
                fixedExtensionService.updateCheckedStatus(99L, true));
    }
}
