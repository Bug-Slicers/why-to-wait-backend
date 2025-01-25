package com.whytowait.domain.dto.fetchMerchantInfo;

import com.whytowait.domain.models.enums.DayOfWeek;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
public class TimingDTO {
    private DayOfWeek dayOfWeek;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean isClosed;
}
