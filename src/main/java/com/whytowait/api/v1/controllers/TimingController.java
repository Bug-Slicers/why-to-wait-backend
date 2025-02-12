package com.whytowait.api.v1.controllers;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.api.common.responses.ApiResponse;
import com.whytowait.api.common.responses.SuccessResponse;
import com.whytowait.api.v1.services.TimingService;
import com.whytowait.domain.dto.time.TimingReqDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/timing")
public class TimingController {

    @Autowired
    TimingService timingService;

    @PostMapping(path = "/create")
    public ApiResponse createRestaurantTiming(@Valid @RequestBody TimingReqDTO requestDTO) throws BadRequestException {
        timingService.createRestaurantTiming(requestDTO);
        return new SuccessResponse<>("Timings Stored Successfully");
    }

    @PostMapping(path = "/update")
    public ApiResponse updateRestaurantTiming(@Valid @RequestBody TimingReqDTO requestDTO) {
        timingService.updateTimings(requestDTO);
        return new SuccessResponse<>("Timings Stored Successfully");
    }
}
