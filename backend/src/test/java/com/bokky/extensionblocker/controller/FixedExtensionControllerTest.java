package com.bokky.extensionblocker.controller;

import com.bokky.extensionblocker.dto.FixedExtensionResponse;
import com.bokky.extensionblocker.service.FixedExtensionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * FixedExtensionController 단위 테스트 클래스
 */
@WebMvcTest(FixedExtensionController.class)
@Import(FixedExtensionControllerTest.MockServiceConfig.class)
class FixedExtensionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FixedExtensionService fixedExtensionService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 고정 확장자 목록 조회 API 테스트
     */
    @Test
    @DisplayName("고정 확장자 목록 조회 API는 래핑된 리스트를 반환해야 한다")
    void getFixedExtensions_shouldReturnWrappedList() throws Exception {
        // given
        List<FixedExtensionResponse> mockResponse = List.of(
                FixedExtensionResponse.builder()
                        .id(1L)
                        .name("exe")
                        .checked(true)
                        .build(),
                FixedExtensionResponse.builder()
                        .id(2L)
                        .name("sh")
                        .checked(false)
                        .build()
        );

        when(fixedExtensionService.getAllFixedExtensions()).thenReturn(mockResponse);

        // when & then
        mockMvc.perform(get("/api/extensions/fixed")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("성공"))
                .andExpect(jsonPath("$.data[0].name").value("exe"))
                .andExpect(jsonPath("$.data[0].checked").value(true))
                .andExpect(jsonPath("$.data[1].name").value("sh"))
                .andExpect(jsonPath("$.data[1].checked").value(false));

        verify(fixedExtensionService, times(1)).getAllFixedExtensions();
    }

    /**
     * 고정 확장자 체크 상태 토글 API 테스트
     */
    @Test
    @DisplayName("고정 확장자 상태 변경 API는 200과 메시지를 반환해야 한다")
    void toggleCheckedStatus_shouldReturnSuccess() throws Exception {
        // given
        Long id = 1L;
        doNothing().when(fixedExtensionService).toggleCheckedStatus(id);

        // when & then
        mockMvc.perform(put("/api/extensions/fixed/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("성공"))
                .andExpect(jsonPath("$.data").value("상태 변경 성공"));

        verify(fixedExtensionService, times(1)).toggleCheckedStatus(id);
    }

    /**
     * 테스트용 Mock 서비스 빈 등록
     */
    @TestConfiguration
    static class MockServiceConfig {
        @Bean
        public FixedExtensionService fixedExtensionService() {
            return mock(FixedExtensionService.class);
        }
    }
}
