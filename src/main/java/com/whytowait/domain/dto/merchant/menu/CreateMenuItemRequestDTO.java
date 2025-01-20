package com.whytowait.domain.dto.merchant.menu;

import com.whytowait.core.annotations.ValidEnum;
import com.whytowait.domain.models.MenuItem;
import com.whytowait.domain.models.enums.ItemTag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateMenuItemRequestDTO {
    @NotNull(message = "MerchantId is mandatory")
    private UUID merchantId;

    @NotNull(message = "Name of the item cannot be empty")
    @Size(min = 3, max = 50, message = "Name of the category must be between 3 and 50 characters")
    private String name;

    private String description;

    @NotNull(message = "Category is mandatory")
    private UUID categoryId;

    @NotNull(message = "Tag is mandatory")
    @ValidEnum(enumClass = ItemTag.class, message = "Invalid Tag")
    private String tag;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be greater than 0")
    private Float Price;

    public static MenuItem toMenuItem(CreateMenuItemRequestDTO data, String imageLink) {
        return MenuItem.builder().merchantId(data.getMerchantId())
                .name(data.getName())
                .description(data.getDescription())
                .categoryId(data.getCategoryId())
                .tag(ItemTag.valueOf(data.getTag()))
                .price(data.getPrice())
                .imageUrl(imageLink)
                .build();
    }

    public static MenuItem toMenuItem(CreateMenuItemRequestDTO data) {
        return MenuItem.builder().merchantId(data.getMerchantId())
                .name(data.getName())
                .description(data.getDescription())
                .categoryId(data.getCategoryId())
                .tag(ItemTag.valueOf(data.getTag()))
                .price(data.getPrice())
                .imageUrl(null)
                .build();
    }
}
