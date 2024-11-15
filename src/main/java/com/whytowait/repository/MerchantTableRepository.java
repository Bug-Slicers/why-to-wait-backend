package com.whytowait.repository;

import com.whytowait.domain.models.MerchantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MerchantTableRepository extends JpaRepository<MerchantTable, UUID> {
}
