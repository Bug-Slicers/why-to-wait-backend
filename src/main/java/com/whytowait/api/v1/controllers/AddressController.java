package com.whytowait.api.v1.controllers;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.api.common.responses.ApiResponse;
import com.whytowait.api.common.responses.SuccessResponse;
import com.whytowait.api.v1.services.AddressService;
import com.whytowait.api.v1.services.JwtService;
import com.whytowait.api.v1.services.MerchantService;
import com.whytowait.domain.dto.merchant.UpdateMerchantAddressReqDTO;
import com.whytowait.domain.models.Address;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    JwtService jwtService;

    @Autowired
    AddressService addressService;

    @PostMapping(path = "/update-address")
    public ApiResponse<Address> updateMerchant(@Valid @RequestBody UpdateMerchantAddressReqDTO requestDTO) throws BadRequestException {
        Address  response = addressService.updateMerchantAddress(requestDTO);
        return new SuccessResponse<Address>("Merchant Address Updated Successfully",response);
    }
}
