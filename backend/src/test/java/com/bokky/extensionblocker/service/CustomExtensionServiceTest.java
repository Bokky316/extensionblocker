package com.bokky.extensionblocker.service;

import com.bokky.extensionblocker.dto.CustomExtensionRequest;
import com.bokky.extensionblocker.dto.CustomExtensionResponse;
import com.bokky.extensionblocker.entity.CustomExtension;
import com.bokky.extensionblocker.exception.DuplicateExtensionException;
import com.bokky.extensionblocker.exception.MaxExtensionLimitException;
import com.bokky.extensionblocker.repository.CustomExtensionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * [단위 테스트] CustomExtensionService
 */
class CustomExtensionServiceTest {

    private CustomExtensionService customExtensionService;
    private CustomExtensionRepository customExtensionRepository;

    @BeforeEach
    void setUp() {
        customExtensionRepository = mock(CustomExtensionRepository.class);
        customExtensionService = new CustomExtensionService(customExtensionRepository);
    }

    @Test
    void 커스텀확장자_정상등록() {
        // given
        CustomExtensionRequest request = new CustomExtensionRequest(" sh ");

        when(customExtensionRepository.count()).thenReturn(100L);
        when(customExtensionRepository.existsByName("sh")).thenReturn(false);
        when(customExtensionRepository.save(any())).thenAnswer(invocation -> {
            CustomExtension ext = invocation.getArgument(0);
            return CustomExtension.builder()
                    .id(1L)
                    .name(ext.getName())
                    .build();
        });

        // when
        CustomExtensionResponse result = customExtensionService.addCustomExtension(request);

        // then
        assertEquals("sh", result.getName());
        assertEquals(1L, result.getId());
    }

    @Test
    void 커스텀확장자_중복시_예외발생() {
        // given
        CustomExtensionRequest request = new CustomExtensionRequest("sh");

        when(customExtensionRepository.count()).thenReturn(100L);
        when(customExtensionRepository.existsByName("sh")).thenReturn(true);

        // when & then
        assertThrows(DuplicateExtensionException.class, () ->
                customExtensionService.addCustomExtension(request));
    }

    @Test
    void 커스텀확장자_개수초과시_예외발생() {
        // given
        CustomExtensionRequest request = new CustomExtensionRequest("exe");

        when(customExtensionRepository.count()).thenReturn(200L);

        // when & then
        assertThrows(MaxExtensionLimitException.class, () ->
                customExtensionService.addCustomExtension(request));
    }
}
