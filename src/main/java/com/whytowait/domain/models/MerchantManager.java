package com.whytowait.domain.models;

import com.whytowait.domain.models.Enums.MerchantRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "merchant_managers")
public class MerchantManager {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @NotNull
    @Column(name = "merchant_id", nullable = false)
    private UUID merchantId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MerchantRole role;
}
