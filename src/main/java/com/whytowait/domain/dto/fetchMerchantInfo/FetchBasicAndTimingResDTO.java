package com.whytowait.domain.dto.fetchMerchantInfo;

import lombok.Data;

import java.util.List;

@Data
public class FetchBasicAndTimingResDTO {
    private FetchBasicInfoDTO basicInfo;
    private List<TimingDTO> timings;
}
