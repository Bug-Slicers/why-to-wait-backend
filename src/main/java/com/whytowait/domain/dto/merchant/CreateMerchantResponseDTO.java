package com.whytowait.domain.dto.merchant;

import com.whytowait.domain.models.Merchant;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateMerchantResponseDTO {
    UUID id;
    String restaurantName;

    public static CreateMerchantResponseDTO fromMerchant(Merchant merchant) {
        return CreateMerchantResponseDTO.builder()
                .id(merchant.getId())
                .restaurantName(merchant.getRestaurantName())
                .build();
    }
}
