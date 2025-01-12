package com.whytowait.domain.dto.fetchMerchantInfo;

import java.time.LocalTime;

public interface FetchTimingProjection {
    String getDayOfWeek();
    LocalTime getOpenTime();
    LocalTime getCloseTime();
    Boolean getIsClosed();
}
