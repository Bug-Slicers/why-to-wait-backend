package com.whytowait.domain.dto.fetchMerchantInfo;

import lombok.Data;

import java.util.List;

@Data
public class FetchMerchantInfoResDTO {
    private FetchBasicInfoAndAddressDTO basicAndAddressInfo;
    private List<TimingDTO> timings;
}
