package com.bokky.extensionblocker.repository;

import com.bokky.extensionblocker.entity.CustomExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 커스텀 확장자 레포지토리 인터페이스
 * - 중복 확인, 정렬 조회 기능 포함
 */
public interface CustomExtensionRepository extends JpaRepository<CustomExtension, Long> {

    /**
     * 이름으로 존재 여부 확인 (중복 방지용)
     * - 소문자로 통일해 비교해야 정확함 (서비스단에서 .toLowerCase() 처리 권장)
     */
    boolean existsByName(String name);

    /**
     * 생성일 기준 최신순으로 전체 조회
     * - UI 상단 노출 목적
     */
    List<CustomExtension> findAllByOrderByCreatedAtDesc();
}
