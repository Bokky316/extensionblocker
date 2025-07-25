package com.bokky.extensionblocker.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 고정 확장자 엔티티 (enum 기반 + checked 상태)
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private FixedExtensionType name;

    @Column(nullable = false)
    private boolean checked;

    public void updateChecked(boolean checked) {
        this.checked = checked;
    }
}
