package com.whytowait.domain.models;

import com.whytowait.domain.models.enums.ItemTag;
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
@Table(name = "menu_item")
public class MenuItem {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "merchant_id", nullable = false)
    private UUID merchantId;

    @NotNull
    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @NotNull
    @Column(name = "category_id", nullable = false)
    private UUID categoryId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemTag tag;

    @NotNull
    @Column(nullable = false)
    private Float price;

    @Column
    private String imageUrl;

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
