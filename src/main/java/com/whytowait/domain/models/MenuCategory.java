package com.whytowait.domain.models;

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

public class MenuCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;

    private String Name;
    private UUID MerchantId;
    private Instant CreatedAt;
    private Instant UpdatedAt;
}
