package com.whytowait.domain.models;

import com.whytowait.domain.models.Enums.OrderItemStatus;
import com.whytowait.domain.models.Enums.OrderStatus;
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

public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;
    private UUID ItemId;
    private UUID OrderId;
    private String Instruction;
    private int Quantity;
    private double Amount;
    private OrderItemStatus ItemStatus;
    private Instant CreatedAt;
}
