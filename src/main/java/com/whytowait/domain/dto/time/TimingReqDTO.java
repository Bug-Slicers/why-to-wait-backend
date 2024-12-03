package com.whytowait.domain.dto.time;

import com.whytowait.core.annotations.UniqueValue;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class TimingReqDTO {
    @NotNull()
    private UUID merchantId;

    @Valid
    @UniqueValue(fieldName = "dayOfWeek", message = "Duplicate Day of the week not allowed")
    private List<TimingItemDTO> timings;
}
