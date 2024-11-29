package com.whytowait.api.v1.services;

import com.whytowait.domain.dto.merchant.CreateMerchantRequestDTO;
import com.whytowait.domain.dto.user.UserDetailsDTO;
import com.whytowait.domain.models.Address;
import com.whytowait.domain.models.Merchant;
import com.whytowait.domain.models.MerchantManager;
import com.whytowait.domain.models.User;
import com.whytowait.domain.models.enums.MerchantRole;
import com.whytowait.repository.AddressRepository;
import com.whytowait.repository.MerchantManagerRepository;
import com.whytowait.repository.MerchantRepository;
import com.whytowait.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MerchantCreateService {

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MerchantManagerRepository merchantManagerRepository;

    public Merchant createMerchant(CreateMerchantRequestDTO createMerchantReqDTO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDTO userDetails = (UserDetailsDTO) authentication.getPrincipal();

        User user =userRepository.findByMobile(userDetails.getMobile());
        Merchant fetchMerchantById = merchantRepository.findByOwnerId(user.getId());
        if(fetchMerchantById!=null){
            return null;
        }

        Address address = Address.builder().addressLine1(createMerchantReqDTO.getAddressLine1())
                .addressLine2(createMerchantReqDTO.getAddressLine2())
                .city(createMerchantReqDTO.getCity())
                .district(createMerchantReqDTO.getDistrict())
                .state(createMerchantReqDTO.getState())
                .pincode(createMerchantReqDTO.getPincode())
                .build();
        Address savedAddress = addressRepository.save(address);

        Merchant merchant = Merchant.builder().restaurantName(createMerchantReqDTO.getRestaurantName())
                .address(savedAddress)
                .owner(user) // Associate owner fetched from username
                .isOnline(false) // Default value
                .build();
        Merchant savedMerchant = merchantRepository.save(merchant);

        MerchantManager merchantManager =MerchantManager.builder().merchantId(savedMerchant.getId()).userId(user.getId()).role(MerchantRole.MERCHANT_OWNER).build();
        MerchantManager savedMerchantManager = merchantManagerRepository.save(merchantManager);
        System.out.println("Saved Merchant Manager :"+savedMerchantManager);

        return savedMerchant;
    }
}
