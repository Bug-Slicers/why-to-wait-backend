package com.whytowait.repository;

import com.whytowait.domain.models.Timing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TimingRepository extends JpaRepository<Timing, UUID> {
    List<Timing> findByMerchantId(UUID merchantId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update timing set is_closed=:isClosed, open_time=:openTime, close_time=:closeTime, updated_at=current_timestamp where merchant_id=:merchantId and day_of_week=CAST(:dayOfWeek AS day_of_week)")
    void updateTimings(@Param("isClosed") boolean isClosed, @Param("openTime") LocalTime openTime, @Param("closeTime") LocalTime closeTime, @Param("merchantId") UUID merchantId, @Param("dayOfWeek") String dayOfWeek);
}
