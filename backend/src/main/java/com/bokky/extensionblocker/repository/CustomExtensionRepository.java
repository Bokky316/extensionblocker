package com.bokky.extensionblocker.repository;

import com.bokky.extensionblocker.entity.CustomExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomExtensionRepository extends JpaRepository<CustomExtension, Long> {
    Optional<CustomExtension> findByName(String name);
    long count();
}
