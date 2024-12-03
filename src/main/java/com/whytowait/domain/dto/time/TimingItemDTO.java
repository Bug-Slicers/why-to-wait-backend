package com.whytowait.domain.dto.time;

import com.whytowait.core.annotations.ValidEnum;
import com.whytowait.domain.models.Timing;
import com.whytowait.domain.models.enums.DayOfWeek;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@Data
public class TimingItemDTO {
    @NotNull(message = "Day of week should not empty")
    @ValidEnum(enumClass = DayOfWeek.class, message = "Invalid day of the week")
    private String dayOfWeek;
    private Boolean isClosed;
    private LocalTime openTime;
    private LocalTime closeTime;

    @AssertTrue(message = "If isClosed is true, openTime and closeTime must be null.")
    private boolean isValidClosedState() {
        return !isClosed || openTime == null && closeTime == null;
    }

    @AssertTrue(message = "If isClosed is false, openTime and closeTime must not be null.")
    private boolean isValidOpenState() {
        return isClosed || openTime != null && closeTime != null;
    }

    @AssertTrue(message = "closeTime should be after openTime")
    private boolean isCloseTimeAfterOpenTime() {
        return openTime == null || closeTime == null || closeTime.isAfter(openTime);
    }

    public static Timing toTiming(UUID merchantId, TimingItemDTO data) {
        return Timing.builder()
                .merchantId(merchantId)
                .dayOfWeek(DayOfWeek.valueOf(data.getDayOfWeek()))
                .isClosed(data.getIsClosed())
                .openTime(data.getOpenTime())
                .closeTime(data.getCloseTime())
                .build();
    }
}
