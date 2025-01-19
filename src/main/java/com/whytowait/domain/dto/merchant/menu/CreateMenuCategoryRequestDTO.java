package com.whytowait.domain.dto.merchant.menu;

import com.whytowait.domain.models.MenuCategory;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateMenuCategoryRequestDTO {

    @NotNull(message = "Name of the category cannot be empty")
    private String Name;

    @NotNull(message = "MerchantId is mandatory")
    private UUID MerchantId;

    public static MenuCategory toMenuCategory(CreateMenuCategoryRequestDTO data) {
        return MenuCategory.builder().name(data.getName())
                .merchantId(data.getMerchantId())
                .build();
    }
}
