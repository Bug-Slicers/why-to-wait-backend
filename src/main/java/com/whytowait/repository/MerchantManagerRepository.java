package com.whytowait.repository;

import com.whytowait.domain.models.MerchantManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MerchantManagerRepository extends JpaRepository<MerchantManager, UUID> {
    List<MerchantManager> findByUserId(UUID userId);
}
