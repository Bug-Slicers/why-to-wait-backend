package com.whytowait.api.v1.services;

import com.whytowait.domain.dto.time.TimingCreationResDTO;
import com.whytowait.domain.dto.time.TimingReqDTO;
import com.whytowait.domain.dto.time.TimingsArrayDTO;
import com.whytowait.domain.models.Merchant;
import com.whytowait.domain.models.Timing;
import com.whytowait.repository.MerchantRepository;
import com.whytowait.repository.TimingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TimingCreationService {

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    TimingRepository timingRepository;

    public TimingCreationResDTO createRestaurantTiming(TimingReqDTO reqDTO){
        Optional<Merchant> merchant = merchantRepository.findById(reqDTO.getMerchantId());
        TimingCreationResDTO response = new TimingCreationResDTO();
        if (merchant.isEmpty()) {
            response.setStatusCode(0);
            response.setMessage("Merchant Not Found");
            return response;
        }
        if(reqDTO.getTimings().size()!=7){
            response.setStatusCode(0);
            response.setMessage("Number of Days must be 7");
            return response;
        }

        for (TimingsArrayDTO dto : reqDTO.getTimings()) {
            Timing timing = Timing.builder()
                    .merchantId(reqDTO.getMerchantId())
                    .dayOfWeek(dto.getDayOfWeek())
                    .isClosed(dto.getIsClosed())
                    .openTime(dto.getOpenTime())
                    .closeTime(dto.getCloseTime())
                    .build();
            Timing savedTiming = timingRepository.save(timing);
            System.out.println("Saved timing :" + savedTiming);
        }
        response.setStatusCode(1);
        response.setMessage("Timings Stored Successfully");
        return response;
    }
}
