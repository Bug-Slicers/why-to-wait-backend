package com.whytowait.api.v1.controllers;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.api.common.responses.ApiResponse;
import com.whytowait.api.common.responses.SuccessResponse;
import com.whytowait.api.v1.services.JwtService;
import com.whytowait.api.v1.services.MerchantService;
import com.whytowait.domain.dto.merchant.CreateMerchantRequestDTO;
import com.whytowait.domain.models.Merchant;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    JwtService jwtService;

    @Autowired
    MerchantService merchantCreateService;

    @PostMapping(path = "/create")
    public ApiResponse<Merchant> createMerchant(@Valid @RequestBody CreateMerchantRequestDTO requestDTO) throws BadRequestException {
        Merchant response = merchantCreateService.createMerchant(requestDTO);
        return new SuccessResponse<Merchant>("Merchant Created Successfully", response);
    }
}
