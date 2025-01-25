package com.whytowait.api.v1.services;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.domain.dto.merchant.CreateMerchantRequestDTO;
import com.whytowait.domain.dto.merchant.UpdateMerchantAddressReqDTO;
import com.whytowait.domain.dto.merchant.UpdateMerchantDetailReqDTO;
import com.whytowait.domain.dto.user.UserDetailsDTO;
import com.whytowait.domain.models.Address;
import com.whytowait.domain.models.Merchant;
import com.whytowait.domain.models.MerchantManager;
import com.whytowait.domain.models.User;
import com.whytowait.domain.models.enums.MerchantRole;
import com.whytowait.repository.MerchantManagerRepository;
import com.whytowait.repository.MerchantRepository;
import com.whytowait.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class MerchantService {

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    AddressService addressService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    MerchantManagerRepository merchantManagerRepository;

    @Transactional
    public Merchant createMerchant(CreateMerchantRequestDTO createMerchantReqDTO) throws BadRequestException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDTO userDetails = (UserDetailsDTO) authentication.getPrincipal();

        User user = userRepository.findByMobile(userDetails.getMobile());
        Merchant fetchMerchantByOwnerId = merchantRepository.findByOwnerId(user.getId());
        if (fetchMerchantByOwnerId != null) {
            throw new BadRequestException("User is already owner of a merchant account");
        }

        Address address = CreateMerchantRequestDTO.toAddress(createMerchantReqDTO);
        Address savedAddress = addressService.createAddress(address);

        Merchant merchant = CreateMerchantRequestDTO.toMerchant(createMerchantReqDTO, savedAddress, user);
        Merchant savedMerchant = merchantRepository.save(merchant);

        MerchantManager merchantManager = MerchantManager.builder().merchantId(savedMerchant.getId()).userId(user.getId()).role(MerchantRole.MERCHANT_OWNER).build();
        merchantManagerRepository.save(merchantManager);

        return savedMerchant;
    }


    @Transactional
    public String updateMerchantBasicInfo(UpdateMerchantDetailReqDTO reqDTO) throws BadRequestException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDTO userDetails = (UserDetailsDTO) authentication.getPrincipal();

        Integer res = merchantRepository.updateMerchantInfo(reqDTO.getRestaurantName(),userDetails.getId());
        if(res==null){
            throw new BadRequestException("Unexpected Exception occured while updating merchantbasic info");
        }
        System.out.println("fetched Merchant using owner Id : "+merchantRepository.findByOwnerId(userDetails.getId()));
        Merchant res2 = merchantRepository.findByOwnerId(userDetails.getId());
        return "Updation Success with new restaurantName :"+res2.getRestaurantName();

    }
}
