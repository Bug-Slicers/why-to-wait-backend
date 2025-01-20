package com.whytowait.domain.dto.merchant.menu;

import com.whytowait.domain.models.MenuCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateMenuCategoryRequestDTO {

    @NotNull(message = "Name of the category cannot be empty")
    @Size(min = 3, max = 50, message = "Name of the category must be between 3 and 50 characters")
    private String name;

    @NotNull(message = "MerchantId is mandatory")
    private UUID merchantId;

    public static MenuCategory toMenuCategory(CreateMenuCategoryRequestDTO data) {
        return MenuCategory.builder().name(data.getName())
                .merchantId(data.getMerchantId())
                .build();
    }
}
