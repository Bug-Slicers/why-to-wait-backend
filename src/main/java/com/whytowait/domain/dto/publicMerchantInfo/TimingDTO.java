package com.whytowait.domain.dto.publicMerchantInfo;

import com.whytowait.domain.models.enums.DayOfWeek;
import lombok.Data;

import java.time.LocalTime;

@Data
public class TimingDTO {
    private DayOfWeek dayOfWeek;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean isClosed;
}
