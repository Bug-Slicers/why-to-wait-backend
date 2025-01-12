package com.whytowait.api.v1.services;

import com.whytowait.domain.dto.fetchMerchantInfo.FetchBasicAndTimingResDTO;
import com.whytowait.domain.dto.fetchMerchantInfo.FetchBasicInfoDTO;
import com.whytowait.domain.dto.fetchMerchantInfo.FetchTimingProjection;
import com.whytowait.domain.dto.fetchMerchantInfo.TimingDTO;
import com.whytowait.domain.models.enums.DayOfWeek;
import com.whytowait.repository.FetchMerchantInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FetchMerchantInfoService {

    @Autowired
    FetchMerchantInfoRepository fetchMerchantInfoRepository;

    public FetchBasicAndTimingResDTO getMerchantUserDetails(UUID mid, String param) {
        FetchBasicAndTimingResDTO resDTO = new FetchBasicAndTimingResDTO();
        if(param.equalsIgnoreCase("basic")){
            FetchBasicInfoDTO fetchBasicInfoDTO= fetchMerchantInfoRepository.fetchMerchantUserBasicDetails(mid);
            resDTO.setBasicInfo(fetchBasicInfoDTO);
            System.out.println("resDTO:"+resDTO);
            return  resDTO;
        }
        else if(param.equalsIgnoreCase("basic address")){
            FetchBasicInfoDTO fetchBasicInfoDTO=fetchMerchantInfoRepository.fetchMerchantUserBasicAndAddressDetails(mid);
            resDTO.setBasicInfo(fetchBasicInfoDTO);
            System.out.println("resDTO:"+resDTO);
            return  resDTO;
        }
        else if(param.equalsIgnoreCase("basic timing")){
            FetchBasicInfoDTO fetchBasicInfoDTO=fetchMerchantInfoRepository.fetchMerchantUserBasicDetails(mid);
            List<FetchTimingProjection> timingProjections = fetchMerchantInfoRepository.fetchTimingDetails(mid);
            resDTO.setBasicInfo(fetchBasicInfoDTO);
            List<TimingDTO> timingDTOs = timingProjections.stream()
                    .map(p -> new TimingDTO(
                            DayOfWeek.valueOf(p.getDayOfWeek()),
                            p.getOpenTime(),
                            p.getCloseTime(),
                            p.getIsClosed()
                    ))
                    .collect(Collectors.toList());
            resDTO.setTimings(timingDTOs);
            System.out.println("resDTO:"+resDTO);
            return  resDTO;
        }
        else if(param.equalsIgnoreCase("basic address timing")){
            FetchBasicInfoDTO fetchBasicInfoDTO=fetchMerchantInfoRepository.fetchMerchantUserBasicAndAddressDetails(mid);
            List<FetchTimingProjection> timingProjections = fetchMerchantInfoRepository.fetchTimingDetails(mid);
            resDTO.setBasicInfo(fetchBasicInfoDTO);
            List<TimingDTO> timingDTOs = timingProjections.stream()
                    .map(p -> new TimingDTO(
                            DayOfWeek.valueOf(p.getDayOfWeek()),
                            p.getOpenTime(),
                            p.getCloseTime(),
                            p.getIsClosed()
                    ))
                    .collect(Collectors.toList());
            resDTO.setTimings(timingDTOs);
            System.out.println("resDTO:"+resDTO);
            return  resDTO;
        }
        return  null;
    }

}
