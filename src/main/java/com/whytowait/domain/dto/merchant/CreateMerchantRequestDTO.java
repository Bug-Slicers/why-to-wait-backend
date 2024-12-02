package com.whytowait.domain.dto.merchant;

import com.whytowait.domain.models.Address;
import com.whytowait.domain.models.Merchant;
import com.whytowait.domain.models.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateMerchantRequestDTO {

    @NotNull(message = "Restaurant name cannot be Empty")
    private String restaurantName;

    @NotNull(message = "Address Line1 is Mandatory")
    private String addressLine1;

    @NotNull(message = "Address Line2 is Mandatory")
    private String addressLine2;

    @NotNull(message = "City is Mandatory")
    private String city;

    @NotNull(message = "District is Mandatory")
    private String district;

    @NotNull(message = "State is Mandatory")
    private String state;

    @NotNull(message = "Pincode is Mandatory")
    @Size(max = 6, min = 6, message = "Enter valid pincode")
    private String pincode;

    public static Merchant toMerchant(CreateMerchantRequestDTO data, Address address, User user) {
        return Merchant.builder().restaurantName(data.getRestaurantName())
                .address(address)
                .owner(user)
                .isOnline(false)
                .build();
    }

    public static Address toAddress(CreateMerchantRequestDTO data) {
        return Address.builder().addressLine1(data.getAddressLine1())
                .addressLine2(data.getAddressLine2())
                .city(data.getCity())
                .district(data.getDistrict())
                .state(data.getState())
                .pincode(data.getPincode())
                .build();
    }
}
