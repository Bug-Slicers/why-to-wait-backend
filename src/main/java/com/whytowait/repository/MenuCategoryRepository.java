package com.whytowait.repository;

import com.whytowait.domain.models.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, UUID> {
    List<MenuCategory> findByMerchantId(UUID id);

    List<MenuCategory> findByMerchantIdAndName(UUID merchantId, String name);

    List<MenuCategory> findByMerchantIdAndId(UUID merchantId, UUID id);
}
