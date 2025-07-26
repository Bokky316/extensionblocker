package com.bokky.extensionblocker.controller;

import com.bokky.extensionblocker.dto.FixedExtensionResponse;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * [단위 테스트] 고정 확장자 컨트롤러
 */
@WebMvcTest(FixedExtensionController.class)
@Import(FixedExtensionControllerTest.MockServiceConfig.class)
class FixedExtensionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FixedExtensionService fixedExtensionService;

    @Test
    @DisplayName("고정 확장자 목록 조회 API는 확장자 리스트를 반환해야 한다")
    void getFixedExtensions_shouldReturnList() throws Exception {
        // given
        List<FixedExtensionResponse> mockResponse = List.of(
                new FixedExtensionResponse(1L, "exe", true),
                new FixedExtensionResponse(2L, "sh", true)
        );
        when(fixedExtensionService.getAllFixedExtensions()).thenReturn(mockResponse);

        // when & then
        mockMvc.perform(get("/api/extensions/fixed")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("exe"))
                .andExpect(jsonPath("$[0].checked").value(true))
                .andExpect(jsonPath("$[1].name").value("sh"))
                .andExpect(jsonPath("$[1].checked").value(true));

        verify(fixedExtensionService, times(1)).getAllFixedExtensions();
    }

    /**
     * 테스트용 목 서비스 빈 등록
     */
    @TestConfiguration
    static class MockServiceConfig {
        @Bean
        public FixedExtensionService fixedExtensionService() {
            return mock(FixedExtensionService.class);
        }
    }
}
