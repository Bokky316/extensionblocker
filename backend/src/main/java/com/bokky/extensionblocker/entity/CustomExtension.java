package com.bokky.extensionblocker.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 커스텀 확장자 엔티티
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CustomExtension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 커스텀 확장자명 (중복 방지를 위해 소문자 + 유니크 제약)
     */
    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * 확장자명을 소문자로 저장
     */
    public void toLowercase() {
        this.name = this.name.toLowerCase();
    }

    public void trim() {
        this.name = this.name.trim();
    }
}
