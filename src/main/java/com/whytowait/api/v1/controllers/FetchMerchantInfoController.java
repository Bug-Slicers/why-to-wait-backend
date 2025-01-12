package com.whytowait.api.v1.controllers;

import com.whytowait.api.common.responses.ApiResponse;
import com.whytowait.api.common.responses.SuccessResponse;
import com.whytowait.api.v1.services.FetchMerchantInfoService;
import com.whytowait.domain.dto.fetchMerchantInfo.FetchBasicAndTimingResDTO;
import com.whytowait.domain.dto.fetchMerchantInfo.FetchBasicInfoDTO;
import com.whytowait.domain.dto.fetchMerchantInfo.FetchMerchantInfoReqDTO;
import com.whytowait.domain.dto.time.TimingReqDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fetch")
public class FetchMerchantInfoController {

    @Autowired
    FetchMerchantInfoService fetchMerchantInfoService;

    @GetMapping(path = "/merchant")
    public SuccessResponse<FetchBasicAndTimingResDTO> fetchMerchantInfo(@RequestParam(name = "query") String query , @RequestBody FetchMerchantInfoReqDTO requestDTO) {

        FetchBasicAndTimingResDTO fetchBasicInfoDTO = fetchMerchantInfoService.getMerchantUserDetails(requestDTO.getMid(), query);
        String responseMessage ="Merchant Data Fetched";
        if(fetchBasicInfoDTO==null){
            responseMessage = "Enter valid queryType ";
        }
        return new SuccessResponse<FetchBasicAndTimingResDTO>(responseMessage,fetchBasicInfoDTO);
    }
}
