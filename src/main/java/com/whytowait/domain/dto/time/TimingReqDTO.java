package com.whytowait.domain.dto.time;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TimingReqDTO {
    @NotNull()
    private UUID merchantId;

    @Valid
    private List<TimingItemDTO> timings;
}
