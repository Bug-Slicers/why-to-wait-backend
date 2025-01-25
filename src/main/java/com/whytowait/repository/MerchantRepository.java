package com.whytowait.repository;

import com.whytowait.domain.models.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
    Merchant findByOwnerId(UUID ownerId);

    @Modifying
    @Query(value = "update merchant set restaurant_name=:restaurantName where owner_id=:merchantId",nativeQuery = true)
    Integer updateMerchantInfo(String restaurantName,UUID merchantId);
}
