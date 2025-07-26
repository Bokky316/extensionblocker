package com.bokky.extensionblocker.controller;

import com.bokky.extensionblocker.dto.CustomExtensionRequest;
import com.bokky.extensionblocker.dto.CustomExtensionResponse;
import com.bokky.extensionblocker.service.CustomExtensionService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * [단위 테스트] CustomExtensionController
 */
@WebMvcTest(CustomExtensionController.class)
@Import(CustomExtensionControllerTest.MockServiceConfig.class)
class CustomExtensionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomExtensionService customExtensionService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 커스텀 확장자 등록 API 성공 케이스 테스트
     */
    @Test
    @DisplayName("POST /api/extensions/custom - 등록 성공 시 200 OK + 데이터 반환")
    void createCustomExtension_shouldReturnCreated() throws Exception {
        // given
        CustomExtensionRequest request = new CustomExtensionRequest("sh");
        CustomExtensionResponse saved = CustomExtensionResponse.builder()
                .id(1L)
                .name("sh")
                .build();

        when(customExtensionService.addCustomExtension(any())).thenReturn(saved);

        // when & then
        mockMvc.perform(post("/api/extensions/custom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("등록 성공"))
                .andExpect(jsonPath("$.data.name").value("sh"));

        verify(customExtensionService, times(1)).addCustomExtension(any());
    }

    /**
     * 커스텀 확장자 삭제 API 성공 케이스 테스트
     */
    @Test
    @DisplayName("DELETE /api/extensions/custom/{id} - 삭제 성공 시 200 OK + 메시지 반환")
    void deleteCustomExtension_shouldReturnOk() throws Exception {
        // given
        Long id = 1L;
        doNothing().when(customExtensionService).deleteCustomExtension(id);

        // when & then
        mockMvc.perform(delete("/api/extensions/custom/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("삭제 성공"))
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(customExtensionService, times(1)).deleteCustomExtension(id);
    }

    /**
     * 테스트용 목 서비스 빈 등록
     */
    @TestConfiguration
    static class MockServiceConfig {
        @Bean
        public CustomExtensionService customExtensionService() {
            return mock(CustomExtensionService.class);
        }
    }
}
