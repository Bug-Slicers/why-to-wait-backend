package com.whytowait.domain.dto.publicMerchantInfo;

import lombok.Data;

@Data
public class PublicBasicInfoAndAddressDTO {
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
