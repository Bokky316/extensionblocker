package com.bokky.extensionblocker.config;

import com.bokky.extensionblocker.entity.FixedExtension;
import com.bokky.extensionblocker.entity.FixedExtensionType;
import com.bokky.extensionblocker.repository.FixedExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 애플리케이션 구동 시 고정 확장자 Seed 주입
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final FixedExtensionRepository fixedExtensionRepository;

    @Override
    public void run(String... args) {
        for (FixedExtensionType type : FixedExtensionType.values()) {
            fixedExtensionRepository.findByName(type)
                    .orElseGet(() -> fixedExtensionRepository.save(
                            FixedExtension.builder()
                                    .name(type)
                                    .checked(true)
                                    .build()));
        }
    }
}
