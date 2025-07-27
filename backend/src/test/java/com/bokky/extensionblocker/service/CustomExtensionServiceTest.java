package com.bokky.extensionblocker.service;

import com.bokky.extensionblocker.dto.CustomExtensionRequest;
import com.bokky.extensionblocker.dto.CustomExtensionResponse;
import com.bokky.extensionblocker.entity.CustomExtension;
import com.bokky.extensionblocker.entity.FixedExtensionType;
import com.bokky.extensionblocker.entity.FixedExtension;
import com.bokky.extensionblocker.exception.DuplicateExtensionException;
import com.bokky.extensionblocker.exception.MaxExtensionLimitException;
import com.bokky.extensionblocker.repository.CustomExtensionRepository;
import com.bokky.extensionblocker.repository.FixedExtensionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
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
    private FixedExtensionRepository fixedExtensionRepository;

    @BeforeEach
    void setUp() {
        customExtensionRepository = mock(CustomExtensionRepository.class);
        fixedExtensionRepository = mock(FixedExtensionRepository.class);
        customExtensionService = new CustomExtensionService(customExtensionRepository, fixedExtensionRepository);
    }

    @Test
    void 커스텀확장자_정상등록() {
        // given
        CustomExtensionRequest request = new CustomExtensionRequest(" sh ");

        when(customExtensionRepository.count()).thenReturn(100L);
        when(customExtensionRepository.existsByName("sh")).thenReturn(false);
        when(fixedExtensionRepository.findByName(FixedExtensionType.SH)).thenReturn(Optional.empty());

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
    void 커스텀확장자_고정확장자_중복시_예외발생() {
        // given
        CustomExtensionRequest request = new CustomExtensionRequest("sh");

        when(fixedExtensionRepository.findByName(FixedExtensionType.SH))
                .thenReturn(Optional.of(FixedExtension.builder()
                        .id(99L)
                        .name(FixedExtensionType.SH)
                        .checked(true)
                        .build()));

        // when & then
        assertThrows(DuplicateExtensionException.class, () ->
                customExtensionService.addCustomExtension(request));
    }

    @Test
    void 커스텀확장자_고정Enum_미포함시_정상진행() {
        // given
        CustomExtensionRequest request = new CustomExtensionRequest("conf");

        // FixedExtensionType.valueOf("conf".toUpperCase()) → IllegalArgumentException 발생 예상
        // 따라서 fixedExtensionRepository는 호출되지 않음

        when(customExtensionRepository.count()).thenReturn(100L);
        when(customExtensionRepository.existsByName("conf")).thenReturn(false);
        when(customExtensionRepository.save(any())).thenAnswer(invocation -> {
            CustomExtension ext = invocation.getArgument(0);
            return CustomExtension.builder()
                    .id(2L)
                    .name(ext.getName())
                    .build();
        });

        // when
        CustomExtensionResponse result = customExtensionService.addCustomExtension(request);

        // then
        assertEquals("conf", result.getName());
        assertEquals(2L, result.getId());
    }

    @Test
    void 커스텀확장자_중복시_예외발생() {
        // given
        CustomExtensionRequest request = new CustomExtensionRequest("sh");

        when(fixedExtensionRepository.findByName(FixedExtensionType.SH)).thenReturn(Optional.empty());
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

    @Test
    void 커스텀확장자_생성일기준_최신순_정렬() {
        // given
        CustomExtension ext1 = CustomExtension.builder()
                .id(1L)
                .name("exe")
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();

        CustomExtension ext2 = CustomExtension.builder()
                .id(2L)
                .name("sh")
                .createdAt(LocalDateTime.now())
                .build();

        when(customExtensionRepository.findAllByOrderByCreatedAtDesc())
                .thenReturn(List.of(ext2, ext1));

        // when
        List<CustomExtensionResponse> result = customExtensionService.getAllCustomExtensions();

        // then
        assertEquals(2, result.size());
        assertEquals("sh", result.get(0).getName());
        assertEquals("exe", result.get(1).getName());
    }
}
