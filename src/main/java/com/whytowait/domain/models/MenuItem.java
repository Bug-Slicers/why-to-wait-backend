package com.whytowait.domain.models;


import com.whytowait.domain.models.Enums.ItemTag;
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
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;

    private UUID MerchantId;
    private String Name;
    private String Description;
    private UUID CategoryId;
    private ItemTag Tag;
    private double Price;
    private String ImageUrl;
    private Instant CreatedAt;
    private Instant UpdatedAt;
}
