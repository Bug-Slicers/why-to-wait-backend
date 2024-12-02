package com.whytowait.domain.dto.time;

import com.whytowait.domain.models.Timing;
import com.whytowait.domain.models.enums.DayOfWeek;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
public class TimingItemDTO {
    @NotNull(message = "Day of week should not empty")
    private DayOfWeek dayOfWeek;
    private Boolean isClosed;
    private LocalTime openTime;
    private LocalTime closeTime;

    @AssertTrue(message = "If isClosed is true, openTime and closeTime must be null.")
    private boolean isValidClosedState() {
        return !isClosed || (openTime == null && closeTime == null);
    }

    @AssertTrue(message = "If isClosed is false, openTime and closeTime must not be null.")
    private boolean isValidOpenState() {
        return isClosed || (openTime != null && closeTime != null);
    }

    public static Timing toTiming(UUID merchantId, TimingItemDTO data) {
        return Timing.builder()
                .merchantId(merchantId)
                .dayOfWeek(data.getDayOfWeek())
                .isClosed(data.getIsClosed())
                .openTime(data.getOpenTime())
                .closeTime(data.getCloseTime())
                .build();
    }
}
