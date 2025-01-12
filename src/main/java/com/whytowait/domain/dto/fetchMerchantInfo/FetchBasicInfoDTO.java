package com.whytowait.domain.dto.fetchMerchantInfo;

import lombok.Data;

@Data

public class FetchBasicInfoDTO {
    private String restaurantName;
    private String name;
    private String email;
    private String mobile;
    private String address;

    public FetchBasicInfoDTO(String restaurantName, String name, String email, String mobile,String address) {
        this.restaurantName = restaurantName;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address=address;
    }

    public FetchBasicInfoDTO(String restaurantName, String name, String email, String mobile) {
        this.restaurantName = restaurantName;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }
}
