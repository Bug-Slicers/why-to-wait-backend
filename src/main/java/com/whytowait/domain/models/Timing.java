package com.whytowait.domain.models;

import com.whytowait.domain.models.Enums.DayOfWeek;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Timing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;
    private UUID MerchantId;
    private DayOfWeek DayOfWeek;
    private Instant OpeningTime;
    private Instant ClosingTime;
    private Boolean CreatedAt;
    private Boolean UpdatedAt;
}
