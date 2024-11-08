package com.whytowait.domain.models;

import com.whytowait.domain.models.Enums.MerchantType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;

    private String RestaurantName;

    @NotNull
    private UUID Address;

    @NotNull
    private UUID Owner;

    @NotNull
    private MerchantType MerchantType;
    private Boolean IsOnline;
    private Instant CreatedAt;
    private Instant LastUpdated;

    @OneToMany(fetch = LAZY, mappedBy="MerchantId")
    Set<Table> Tables;

    @OneToMany(fetch = LAZY, mappedBy="MerchantId")
    Set<MenuItem> MenuItems;

    @OneToOne(mappedBy = "MerchantId")
    Timing Time;

    @OneToMany(fetch = LAZY, mappedBy="MerchantId")
    Set<Qrcode> QrCodes;

    @OneToMany(fetch = LAZY, mappedBy="MerchantId")
    Set<Order> Orders;

    @OneToMany(fetch = LAZY, mappedBy="MerchantId")
    Set<MenuCategory> MenuCategories;

}
