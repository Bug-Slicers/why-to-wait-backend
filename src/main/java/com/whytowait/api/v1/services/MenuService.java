package com.whytowait.api.v1.services;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.domain.dto.merchant.menu.CreateMenuCategoryRequestDTO;
import com.whytowait.domain.dto.merchant.menu.CreateMenuItemRequestDTO;
import com.whytowait.domain.models.MenuCategory;
import com.whytowait.domain.models.MenuItem;
import com.whytowait.repository.MenuCategoryRepository;
import com.whytowait.repository.MenuItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class MenuService {

    @Autowired
    MenuCategoryRepository menuCategoryRepository;

    @Autowired
    CloudflareR2Service cloudflareR2Service;

    @Autowired
    MenuItemRepository menuItemRepository;

    @Transactional
    public MenuCategory CreateMenuCategory(CreateMenuCategoryRequestDTO requestDTO) throws BadRequestException {

        List<String> menuCategories = menuCategoryRepository.findByMerchantId(requestDTO.getMerchantId()).stream()
                .map(MenuCategory::getName)
                .toList();
        if (menuCategories.contains(requestDTO.getName())) {
            throw new BadRequestException("Category already Exists");
        }

        MenuCategory menuCategory = CreateMenuCategoryRequestDTO.toMenuCategory(requestDTO);
        menuCategoryRepository.save(menuCategory);

        return menuCategory;
    }

    public MenuItem CreateMenuItem(CreateMenuItemRequestDTO requestDTO, MultipartFile image) throws BadRequestException {

        List<String> menuCategories = menuCategoryRepository.findByMerchantId(requestDTO.getMerchantId()).stream()
                .map(MenuCategory::getName)
                .toList();

        if (!menuCategories.contains(requestDTO.getName())) {
            throw new BadRequestException("Category Does not Exists");
        }

        String filePath = requestDTO.getMerchantId().toString() + "/" + image.getOriginalFilename();
        String imageLink = cloudflareR2Service.UploadFile(filePath, image);

        MenuItem menuItem = CreateMenuItemRequestDTO.toMenuItem(requestDTO, imageLink);
        menuItemRepository.save(menuItem);

        return menuItem;
    }
}
