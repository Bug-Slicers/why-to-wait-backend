package com.whytowait.api.v1.controllers.publicPackage;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.api.common.responses.SuccessResponse;
import com.whytowait.api.v1.services.FetchMerchantInfoService;
import com.whytowait.domain.dto.fetchMerchantInfo.FetchMerchantInfoResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/public")
public class FetchMerchantInfoController {

    @Autowired
    FetchMerchantInfoService fetchMerchantInfoService;

    @GetMapping(path = "/merchants")
    public SuccessResponse<FetchMerchantInfoResDTO> fetchMerchantInfo(@RequestParam(name = "merchantId") UUID merchantId, @RequestParam(name = "query") String query) throws BadRequestException {

        FetchMerchantInfoResDTO fetchBasicInfoDTO = fetchMerchantInfoService.getMerchantUserDetails(merchantId, query);
        return new SuccessResponse<FetchMerchantInfoResDTO>("Data Fetched Successfully!",fetchBasicInfoDTO);
    }
}
