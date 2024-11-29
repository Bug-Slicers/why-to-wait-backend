package com.whytowait.api.v1.controllers;

import com.whytowait.api.common.responses.SuccessResponse;
import com.whytowait.api.v1.services.JwtService;
import com.whytowait.api.v1.services.TimingUpdationService;
import com.whytowait.domain.dto.time.TimingReqDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/timing")
public class TimingsUpdationController {

    @Autowired
    JwtService jwtService;

    @Autowired
    TimingUpdationService timingUpdationService;


    @PostMapping(path = "/update")
    public SuccessResponse<String> updateRestaurantTiming(@Valid @RequestBody TimingReqDTO requestDTO, @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        String response = timingUpdationService.updateTimings(requestDTO);
        return  new SuccessResponse<String>("Timings Stored Successfully",response);
    }
}
