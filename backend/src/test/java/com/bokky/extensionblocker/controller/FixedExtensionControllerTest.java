package com.bokky.extensionblocker.controller;

import com.bokky.extensionblocker.dto.FixedExtensionResponse;
import com.bokky.extensionblocker.entity.FixedExtensionType;
import com.bokky.extensionblocker.service.FixedExtensionService;
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

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * [단위 테스트] FixedExtensionController
 * - 고정 확장자 전체 조회
 * - 고정 확장자 상태 토글
 */
@WebMvcTest(FixedExtensionController.class)
@Import(FixedExtensionControllerTest.MockServiceConfig.class)
class FixedExtensionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FixedExtensionService fixedExtensionService;

    @Test
    @DisplayName("고정 확장자 목록 조회 API는 래핑된 리스트를 반환해야 한다")
    void getFixedExtensions_shouldReturnWrappedList() throws Exception {
        // given
        List<FixedExtensionResponse> mockResponse = List.of(
                FixedExtensionResponse.builder()
                        .id(1L)
                        .name(FixedExtensionType.EXE)
                        .checked(true)
                        .build(),
                FixedExtensionResponse.builder()
                        .id(2L)
                        .name(FixedExtensionType.SH)
                        .checked(false)
                        .build()
        );

        when(fixedExtensionService.getAllFixedExtensions()).thenReturn(mockResponse);

        // when & then
        mockMvc.perform(get("/api/fixed")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("성공"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("EXE"))
                .andExpect(jsonPath("$.data[0].checked").value(true))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("SH"))
                .andExpect(jsonPath("$.data[1].checked").value(false));

        verify(fixedExtensionService, times(1)).getAllFixedExtensions();
    }

    @Test
    @DisplayName("고정 확장자 상태 변경 API는 200과 메시지를 반환해야 한다")
    void toggleCheckedStatus_shouldReturnSuccess() throws Exception {
        // given
        Long id = 1L;
        doNothing().when(fixedExtensionService).toggleCheckedStatus(id);

        // when & then
        mockMvc.perform(put("/api/fixed/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("상태 변경 성공"))
                .andExpect(jsonPath("$.data").doesNotExist()); // nullValue → doesNotExist로 변경 가능

        verify(fixedExtensionService, times(1)).toggleCheckedStatus(id);
    }

    /**
     * 테스트용 MockService Bean 설정
     */
    @TestConfiguration
    static class MockServiceConfig {
        @Bean
        public FixedExtensionService fixedExtensionService() {
            return mock(FixedExtensionService.class);
        }
    }
}
