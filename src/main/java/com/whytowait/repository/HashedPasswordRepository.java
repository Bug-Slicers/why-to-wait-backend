package com.whytowait.repository;

import com.whytowait.domain.models.HashedPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HashedPasswordRepository extends JpaRepository<HashedPassword, UUID> {
}
