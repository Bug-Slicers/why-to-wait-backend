package com.whytowait.domain.dto.merchant;

import lombok.Data;

@Data
public class UpdateMerchantAddressReqDTO {
    private String addressLine1;

    private String addressLine2;

    private String city;

    private String district;
    private String state;

    private String pincode;
}
