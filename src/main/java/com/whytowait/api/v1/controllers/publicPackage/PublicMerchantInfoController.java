package com.whytowait.api.v1.controllers.publicPackage;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.api.common.responses.SuccessResponse;
import com.whytowait.api.v1.services.PublicMerchantInfoService;
import com.whytowait.domain.dto.publicMerchantInfo.PublicMerchantInfoResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/public")
public class PublicMerchantInfoController {

    @Autowired
    PublicMerchantInfoService publicMerchantInfoService;

    @GetMapping(path = "/merchants")
    public SuccessResponse<PublicMerchantInfoResDTO> fetchMerchantInfo(@RequestParam(name = "merchantId") UUID merchantId, @RequestParam(name = "query") String query) throws BadRequestException {

        PublicMerchantInfoResDTO fetchBasicInfoDTO = publicMerchantInfoService.fetchMerchantInfo(merchantId, query);
        return new SuccessResponse<PublicMerchantInfoResDTO>("Data Fetched Successfully!", fetchBasicInfoDTO);
    }
}
