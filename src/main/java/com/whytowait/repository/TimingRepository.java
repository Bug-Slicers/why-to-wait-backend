package com.whytowait.repository;

import com.whytowait.domain.models.Timing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TimingRepository extends JpaRepository<Timing, UUID> {
}
