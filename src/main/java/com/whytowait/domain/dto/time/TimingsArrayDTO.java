package com.whytowait.domain.dto.time;

import com.whytowait.domain.models.enums.DayOfWeek;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.sql.Time;
import java.time.LocalTime;

@Data
public class TimingsArrayDTO {
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
}
