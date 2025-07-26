package com.bokky.extensionblocker.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 고정 확장자 Entity
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FixedExtension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 확장자 이름 (enum 기반)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private FixedExtensionType name;

    /**
     * 체크 여부
     */
    @Column(nullable = false)
    private boolean checked;

    /**
     * checked 상태 토글 메서드
     */
    public void toggleChecked() {
        this.checked = !this.checked;
    }
}
