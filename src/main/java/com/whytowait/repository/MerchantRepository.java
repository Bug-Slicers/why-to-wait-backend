package com.whytowait.repository;

import com.whytowait.domain.models.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
    Merchant findByOwnerId(UUID ownerId);
}
