package com.whytowait.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "merchant_table", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"merchant_id", "tableNumber"})
})
public class MerchantTable {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "merchant_id", nullable = false)
    private UUID merchantId;

    @NotNull
    @Column(nullable = false)
    private int tableNumber;

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
