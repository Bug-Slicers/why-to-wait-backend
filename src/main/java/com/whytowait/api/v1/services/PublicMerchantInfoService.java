package com.whytowait.api.v1.services;

import com.whytowait.api.common.enums.MerchantInfoQueryParamsType;
import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.domain.dto.publicMerchantInfo.PublicMerchantInfoResDTO;
import com.whytowait.domain.dto.publicMerchantInfo.PublicBasicInfoAndAddressDTO;
import com.whytowait.domain.dto.publicMerchantInfo.TimingDTO;
import com.whytowait.repository.PublicMerchantInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PublicMerchantInfoService {

    @Autowired
    PublicMerchantInfoRepo publicMerchantInfoRepo;

    public PublicMerchantInfoResDTO fetchMerchantInfo(UUID mid, String param) throws BadRequestException {
        PublicMerchantInfoResDTO resDTO = new PublicMerchantInfoResDTO();
        MerchantInfoQueryParamsType merchantInfoQueryParamsType = MerchantInfoQueryParamsType.fromString(param);

        if (merchantInfoQueryParamsType == MerchantInfoQueryParamsType.BASIC) {
            PublicBasicInfoAndAddressDTO publicBasicInfoAndAddressDTO = publicMerchantInfoRepo.fetchMerchantUserBasicDetails(mid);
            resDTO.setBasicAndAddressInfo(publicBasicInfoAndAddressDTO);
            System.out.println("resDTO:" + resDTO);
            return resDTO;
        } else if (merchantInfoQueryParamsType == MerchantInfoQueryParamsType.BASIC_ADDRESS) {
            PublicBasicInfoAndAddressDTO publicBasicInfoAndAddressDTO = publicMerchantInfoRepo.fetchMerchantUserBasicAndAddressDetails(mid);
            resDTO.setBasicAndAddressInfo(publicBasicInfoAndAddressDTO);
            System.out.println("resDTO:" + resDTO);
            return resDTO;
        } else if (merchantInfoQueryParamsType == MerchantInfoQueryParamsType.BASIC_TIMING) {
            PublicBasicInfoAndAddressDTO publicBasicInfoAndAddressDTO = publicMerchantInfoRepo.fetchMerchantUserBasicDetails(mid);
            List<TimingDTO> timings = publicMerchantInfoRepo.fetchTimingDetails(mid);
            resDTO.setBasicAndAddressInfo(publicBasicInfoAndAddressDTO);
            resDTO.setTimings(timings);
            System.out.println("resDTO:" + resDTO);
            return resDTO;
        } else if (merchantInfoQueryParamsType == MerchantInfoQueryParamsType.BASIC_ADDRESS_TIMING) {
            PublicBasicInfoAndAddressDTO publicBasicInfoAndAddressDTO = publicMerchantInfoRepo.fetchMerchantUserBasicAndAddressDetails(mid);
            List<TimingDTO> timings = publicMerchantInfoRepo.fetchTimingDetails(mid);
            resDTO.setBasicAndAddressInfo(publicBasicInfoAndAddressDTO);
            resDTO.setTimings(timings);
            System.out.println("resDTO:" + resDTO);
            return resDTO;
        } else {
            throw new BadRequestException("Invalid parameter query type");
        }
    }

}
