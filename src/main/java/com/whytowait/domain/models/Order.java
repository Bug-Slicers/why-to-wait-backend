package com.whytowait.domain.models;

import com.whytowait.domain.models.Enums.OrderStatus;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.UUID;

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;

    private int Token;
    private UUID MerchantId;
    private UUID TableId;
    private OrderStatus Status;
    private double TotalAmount;
    private Instant CreatedAt;
    private Instant UpdatedAt;
}
