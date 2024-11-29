package com.whytowait.domain.dto.time;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TimingReqDTO {
    private UUID merchantId;
    @Valid
    private List<TimingsArrayDTO> timings;

}
