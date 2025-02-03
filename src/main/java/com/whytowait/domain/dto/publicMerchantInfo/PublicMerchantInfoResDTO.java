package com.whytowait.domain.dto.publicMerchantInfo;

import lombok.Data;

import java.util.List;

@Data
public class PublicMerchantInfoResDTO {
    private PublicBasicInfoAndAddressDTO basicAndAddressInfo;
    private List<TimingDTO> timings;
}
