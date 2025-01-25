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

//    public TimingDTO(DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime, Boolean isClosed) {
//        this.dayOfWeek = dayOfWeek;
//        this.openTime = openTime;
//        this.closeTime = closeTime;
//        this.isClosed = isClosed;
//    }
}
