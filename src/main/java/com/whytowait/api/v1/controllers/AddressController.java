package com.whytowait.api.v1.controllers;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.api.common.responses.ApiResponse;
import com.whytowait.api.common.responses.SuccessResponse;
import com.whytowait.api.v1.services.AddressService;
import com.whytowait.api.v1.services.JwtService;
import com.whytowait.api.v1.services.MerchantService;
import com.whytowait.core.annotations.aspects.CheckMerchantManagerRole;
import com.whytowait.core.annotations.enums.RequestSource;
import com.whytowait.domain.dto.merchant.UpdateMerchantAddressReqDTO;
import com.whytowait.domain.models.Address;
import com.whytowait.domain.models.enums.MerchantRole;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    JwtService jwtService;

    @Autowired
    AddressService addressService;

    @PutMapping(path = "/update-address/{merchantId}")
    @CheckMerchantManagerRole(requiredAuthority = MerchantRole.MERCHANT_OWNER, fieldName = "merchantId", source = RequestSource.PARAM)
    public ApiResponse<Address> updateMerchant(@PathVariable UUID merchantId, @Valid @RequestBody UpdateMerchantAddressReqDTO requestDTO) throws BadRequestException {
        Address response = addressService.updateMerchantAddress(merchantId, requestDTO);
        return new SuccessResponse<Address>("Merchant Address Updated Successfully", response);
    }
}
