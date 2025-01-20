package com.whytowait.domain.dto.merchant.menu;

import com.whytowait.domain.models.MenuCategory;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateMenuCategoryResponseDTO {

    private UUID id;
    private String categoryName;

    public static CreateMenuCategoryResponseDTO fromMenuCategory(MenuCategory menuCategory) {
        return CreateMenuCategoryResponseDTO.builder()
                .id(menuCategory.getId())
                .categoryName(menuCategory.getName())
                .build();
    }
}
