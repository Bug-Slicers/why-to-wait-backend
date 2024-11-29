package com.whytowait.domain.dto.merchant;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateMerchantRequestDTO {

    @NotNull(message = "Restaurant name cannot be Empty")
    private String restaurantName;

    @NotNull(message = "Address Line1 is Mandetory")
    private String addressLine1;

    @NotNull(message = "Address Line2 is Mandetory")
    private String addressLine2;

    @NotNull(message = "City is Mandetory")
    private String city;

    @NotNull(message = "District is Mandetory")
    private String district;

    @NotNull(message = "State is Mandetory")
    private String state;
    
    @NotNull(message = "Pincode is Mandetory")
    @Size(max = 6,min = 6,message = "Enter valid pincode")
    private String pincode;
    
}
