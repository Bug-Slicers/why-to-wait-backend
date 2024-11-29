package com.whytowait.api.v1.controllers;

import com.whytowait.api.common.responses.ApiResponse;
import com.whytowait.api.common.responses.BadRequestResponse;
import com.whytowait.api.common.responses.SuccessResponse;
import com.whytowait.api.v1.services.JwtService;
import com.whytowait.api.v1.services.TimingCreationService;
import com.whytowait.domain.dto.time.TimingCreationResDTO;
import com.whytowait.domain.dto.time.TimingReqDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/timing")
public class TimingsCreationController {

    @Autowired
    JwtService jwtService;

    @Autowired
    TimingCreationService timingRequestService;


    @PostMapping(path = "/create")
    public ApiResponse<TimingCreationResDTO> createRestaurantTiming(@Valid @RequestBody TimingReqDTO requestDTO, @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
         TimingCreationResDTO response = timingRequestService.createRestaurantTiming(requestDTO);

         if(response.getStatusCode()==0){
             return new BadRequestResponse<>(response.toString());
         }
         return  new SuccessResponse<TimingCreationResDTO>("Timings Stored Successfully",response);
    }
}
