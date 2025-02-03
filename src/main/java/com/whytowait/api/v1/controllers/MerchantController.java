package com.whytowait.api.v1.controllers;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.api.common.responses.ApiResponse;
import com.whytowait.api.common.responses.SuccessResponse;
import com.whytowait.api.v1.services.JwtService;
import com.whytowait.api.v1.services.MerchantService;
import com.whytowait.core.annotations.aspects.CheckMerchantManagerRole;
import com.whytowait.core.annotations.enums.RequestSource;
import com.whytowait.domain.dto.merchant.CreateMerchantRequestDTO;
import com.whytowait.domain.dto.merchant.CreateMerchantResponseDTO;
import com.whytowait.domain.dto.merchant.UpdateMerchantAddressReqDTO;
import com.whytowait.domain.dto.merchant.UpdateMerchantDetailReqDTO;
import com.whytowait.domain.models.Address;
import com.whytowait.domain.models.Merchant;
import com.whytowait.domain.models.User;
import com.whytowait.domain.models.enums.MerchantRole;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    JwtService jwtService;

    @Autowired
    MerchantService merchantService;

    @PostMapping(path = "/create")
    public ApiResponse<CreateMerchantResponseDTO> createMerchant(@Valid @RequestBody CreateMerchantRequestDTO requestDTO) throws BadRequestException {
        Merchant response = merchantService.createMerchant(requestDTO);
        return new SuccessResponse<CreateMerchantResponseDTO>("Merchant Created Successfully", CreateMerchantResponseDTO.fromMerchant(response));
    }

    @PutMapping(path = "/update-basic/{merchantId}")
    @CheckMerchantManagerRole(requiredAuthority = MerchantRole.MERCHANT_OWNER, fieldName = "merchantId", source = RequestSource.PARAM)
    public ApiResponse<String> updateBasicInfo(@PathVariable UUID merchantId, @Valid @RequestBody UpdateMerchantDetailReqDTO requestDTO) throws BadRequestException {
        String res = merchantService.updateMerchantBasicInfo(merchantId, requestDTO);
        return new SuccessResponse<String>("Merchant Updated Successfully", res);
    }
}
