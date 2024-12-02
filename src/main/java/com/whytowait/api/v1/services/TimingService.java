package com.whytowait.api.v1.services;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.domain.dto.time.TimingReqDTO;
import com.whytowait.domain.dto.time.TimingItemDTO;
import com.whytowait.domain.models.Merchant;
import com.whytowait.domain.models.Timing;
import com.whytowait.repository.MerchantRepository;
import com.whytowait.repository.TimingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class TimingService {

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    TimingRepository timingRepository;

    @Transactional
    public void createRestaurantTiming(TimingReqDTO reqDTO) throws BadRequestException {
        Optional<Merchant> merchant = merchantRepository.findById(reqDTO.getMerchantId());
        if (merchant.isEmpty()) {
            throw new BadRequestException("Merchant doesn't exists");
        }

        if (reqDTO.getTimings().size() != 7) {
            throw new BadRequestException("All days should be there");
        }

        for (TimingItemDTO dto : reqDTO.getTimings()) {
            Timing timing = TimingItemDTO.toTiming(reqDTO.getMerchantId(), dto);
            Timing savedTiming = timingRepository.save(timing);
        }
    }

    public String updateTimings(TimingReqDTO timingReqDTO) {
        UUID merchantId = timingReqDTO.getMerchantId();
        for (TimingItemDTO dto : timingReqDTO.getTimings()) {
            Integer updateTimingStatus = timingRepository.updateTimings(dto.getIsClosed(), dto.getOpenTime(), dto.getCloseTime(), merchantId, dto.getDayOfWeek().name());
        }
        return "Update Success";
    }
}