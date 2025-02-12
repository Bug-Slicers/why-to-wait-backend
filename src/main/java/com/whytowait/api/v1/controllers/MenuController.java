package com.whytowait.api.v1.controllers;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.api.common.responses.ApiResponse;
import com.whytowait.api.common.responses.SuccessResponse;
import com.whytowait.api.v1.services.MenuService;
import com.whytowait.core.annotations.aspects.CheckMerchantManagerRole;
import com.whytowait.core.annotations.enums.RequestSource;
import com.whytowait.domain.dto.merchant.menu.CreateMenuCategoryRequestDTO;
import com.whytowait.domain.dto.merchant.menu.CreateMenuCategoryResponseDTO;
import com.whytowait.domain.dto.merchant.menu.CreateMenuItemRequestDTO;
import com.whytowait.domain.models.MenuCategory;
import com.whytowait.domain.models.MenuItem;
import com.whytowait.domain.models.enums.MerchantRole;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("merchant/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @CheckMerchantManagerRole(requiredAuthority = MerchantRole.MERCHANT_OWNER, fieldName = "merchantId", source = RequestSource.BODY)
    @PostMapping(path = "/create-category")
    public ApiResponse<CreateMenuCategoryResponseDTO> createMenuCategory(@Valid @RequestBody CreateMenuCategoryRequestDTO requestDTO) throws BadRequestException {
        MenuCategory response = menuService.createMenuCategory(requestDTO);
        return new SuccessResponse<>("Menu Category Created Successfully", CreateMenuCategoryResponseDTO.fromMenuCategory(response));
    }

    @CheckMerchantManagerRole(requiredAuthority = MerchantRole.MERCHANT_OWNER, fieldName = "merchantId", source = RequestSource.BODY)
    @PostMapping(path = "/create-item", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<MenuItem> createMenuItem(@RequestPart(value = "imageFile", required = false) MultipartFile imageFile, @Valid @RequestPart("menuItem") CreateMenuItemRequestDTO requestDTO) throws BadRequestException, IOException {
        MenuItem response = menuService.createMenuItem(requestDTO, imageFile);
        return new SuccessResponse<>("Menu Item Created Successfully", response);
    }


}


