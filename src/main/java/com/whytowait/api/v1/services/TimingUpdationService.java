package com.whytowait.api.v1.services;

import com.whytowait.domain.dto.time.TimingReqDTO;
import com.whytowait.domain.dto.time.TimingsArrayDTO;
import com.whytowait.domain.models.enums.DayOfWeek;
import com.whytowait.repository.MerchantRepository;
import com.whytowait.repository.TimingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class TimingUpdationService {

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    TimingRepository timingRepository;


    public String updateTimings(TimingReqDTO timingReqDTO){
        UUID merchantId = timingReqDTO.getMerchantId();
        for (TimingsArrayDTO dto: timingReqDTO.getTimings()){
            Integer updateTimingStatus = timingRepository.updateTimings(dto.getIsClosed(), dto.getOpenTime(), dto.getCloseTime(), merchantId, dto.getDayOfWeek().name());
            System.out.println("time update status :"+dto.getDayOfWeek()+" , "+updateTimingStatus);
        }
        return  "Updation Success";
    }
}
