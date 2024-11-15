package com.whytowait.repository;

import com.whytowait.domain.models.MerchantManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MerchantManagerRepository extends JpaRepository<MerchantManager, UUID> {
}
