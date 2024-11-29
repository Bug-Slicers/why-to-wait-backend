package com.whytowait.api.v1.controllers;

import com.whytowait.api.common.exceptions.*;
import com.whytowait.api.common.responses.ApiResponse;
import com.whytowait.api.common.responses.BadRequestResponse;
import com.whytowait.api.common.responses.SuccessResponse;
import com.whytowait.api.v1.services.JwtService;
import com.whytowait.api.v1.services.MerchantCreateService;
import com.whytowait.domain.dto.merchant.CreateMerchantRequestDTO;
import com.whytowait.domain.dto.user.UserLoginReqDTO;
import com.whytowait.domain.dto.user.UserLoginResDTO;
import com.whytowait.domain.dto.user.UserRegistrationDTO;
import com.whytowait.domain.models.Merchant;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/merchant")
public class MerchantCreateController {

    @Autowired
    JwtService jwtService;

    @Autowired
    MerchantCreateService merchantCreateService;

    @PostMapping(path = "/create")
    public ApiResponse<Merchant> createMerchant(@Valid @RequestBody CreateMerchantRequestDTO requestDTO, @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        Merchant response = merchantCreateService.createMerchant(requestDTO);
        if(response==null){
            return new BadRequestResponse<>("Merchant Already Exists");
        }
        return new SuccessResponse<Merchant>("Merchant Created Successfully", response);
    }
}
