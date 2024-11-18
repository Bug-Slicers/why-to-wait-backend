package com.whytowait.domain.models;

import com.whytowait.domain.models.enums.OrderItemStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_item")
@Builder
public class OrderItem {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "item_id", nullable = false)
    private UUID itemId;

    @NotNull
    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    private String instruction;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private float amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private OrderItemStatus status = OrderItemStatus.PREPARING;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        createdAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        // No need to update createdAt as it is set only once during persist
    }
}
