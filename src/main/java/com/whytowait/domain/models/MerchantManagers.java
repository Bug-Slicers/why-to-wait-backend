package com.whytowait.domain.models;

import com.whytowait.domain.models.Enums.MerchantRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class MerchantManagers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;


    private UUID MerchantId;
    private UUID UserId;
    private MerchantRole Role;
}
