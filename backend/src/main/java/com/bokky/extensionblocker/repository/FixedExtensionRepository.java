package com.bokky.extensionblocker.repository;

import com.bokky.extensionblocker.entity.FixedExtension;
import com.bokky.extensionblocker.entity.FixedExtensionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FixedExtensionRepository extends JpaRepository<FixedExtension, Long> {
    Optional<FixedExtension> findByName(FixedExtensionType name);
}
