package com.whytowait.domain.models;

import com.whytowait.domain.models.Enums.DayOfWeek;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "timing", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"merchant_id", "dayOfWeek"})
})
public class Timing {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "merchant_id", nullable = false)
    private UUID merchantId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Column
    private LocalTime openTime;

    @Column
    private LocalTime closeTime;

    @Column(nullable = false)
    private Boolean isClosed = false;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
