package com.whytowait.api.v1.services;

import com.whytowait.api.common.enums.ParamType;
import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.domain.dto.fetchMerchantInfo.FetchMerchantInfoResDTO;
import com.whytowait.domain.dto.fetchMerchantInfo.FetchBasicInfoAndAddressDTO;
import com.whytowait.domain.dto.fetchMerchantInfo.TimingDTO;
import com.whytowait.repository.FetchMerchantInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FetchMerchantInfoService {

    @Autowired
    FetchMerchantInfoRepo fetchMerchantInfoRepo;

    public FetchMerchantInfoResDTO getMerchantUserDetails(UUID mid, String param) throws BadRequestException {
        FetchMerchantInfoResDTO resDTO = new FetchMerchantInfoResDTO();
        ParamType paramType = ParamType.fromString(param);

        if(paramType == ParamType.BASIC){
            FetchBasicInfoAndAddressDTO fetchBasicInfoAndAddressDTO = fetchMerchantInfoRepo.fetchMerchantUserBasicDetails(mid);
            resDTO.setBasicAndAddressInfo(fetchBasicInfoAndAddressDTO);
            System.out.println("resDTO:"+resDTO);
            return  resDTO;
        }
        else if(paramType == ParamType.BASIC_ADDRESS){
            FetchBasicInfoAndAddressDTO fetchBasicInfoAndAddressDTO =fetchMerchantInfoRepo.fetchMerchantUserBasicAndAddressDetails(mid);
            resDTO.setBasicAndAddressInfo(fetchBasicInfoAndAddressDTO);
            System.out.println("resDTO:"+resDTO);
            return  resDTO;
        }
        else if(paramType == ParamType.BASIC_TIMING){
            FetchBasicInfoAndAddressDTO fetchBasicInfoAndAddressDTO =fetchMerchantInfoRepo.fetchMerchantUserBasicDetails(mid);
            List<TimingDTO> timings = fetchMerchantInfoRepo.fetchTimingDetails(mid);
            resDTO.setBasicAndAddressInfo(fetchBasicInfoAndAddressDTO);
            resDTO.setTimings(timings);
            System.out.println("resDTO:"+resDTO);
            return  resDTO;
        }
        else if(paramType == ParamType.BASIC_ADDRESS_TIMING){
            FetchBasicInfoAndAddressDTO fetchBasicInfoAndAddressDTO =fetchMerchantInfoRepo.fetchMerchantUserBasicAndAddressDetails(mid);
            List<TimingDTO> timings = fetchMerchantInfoRepo.fetchTimingDetails(mid);
            resDTO.setBasicAndAddressInfo(fetchBasicInfoAndAddressDTO);
            resDTO.setTimings(timings);
            System.out.println("resDTO:"+resDTO);
            return  resDTO;
        }
        else{
            throw new BadRequestException("Invalid parameter query type");
        }
    }

}
