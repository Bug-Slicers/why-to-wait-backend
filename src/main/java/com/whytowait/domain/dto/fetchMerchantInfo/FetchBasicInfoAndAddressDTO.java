package com.whytowait.domain.dto.fetchMerchantInfo;

import lombok.Data;

@Data
public class FetchBasicInfoAndAddressDTO {
    private String restaurantName;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String pincode;

}
