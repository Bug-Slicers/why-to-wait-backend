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


    public Address updateMerchantAddress(UpdateMerchantAddressReqDTO updateMerchantAddressReqDTO) throws BadRequestException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDTO userDetails = (UserDetailsDTO) authentication.getPrincipal();
        User user = userRepository.findByMobile(userDetails.getMobile());
        Merchant fetchMerchantByOwnerId = merchantRepository.findByOwnerId(user.getId());
        Address address = Address.builder().addressLine1(updateMerchantAddressReqDTO.getAddressLine1())
                .addressLine2(updateMerchantAddressReqDTO.getAddressLine2())
                .city(updateMerchantAddressReqDTO.getCity())
                .district(updateMerchantAddressReqDTO.getDistrict())
                .state(updateMerchantAddressReqDTO.getState())
                .pincode(updateMerchantAddressReqDTO.getPincode())
                .build();

        int resMsg= addressService.updateAddress(address,fetchMerchantByOwnerId.getAddress().getId());
        if(resMsg==1){
            return  address;
        }
        return  null;
    }

    public User updateMerchantBasicInfo(UpdateMerchantDetailReqDTO reqDTO) throws BadRequestException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDTO userDetails = (UserDetailsDTO) authentication.getPrincipal();
        User userToUpdate = Objects.requireNonNull(User.builder().
                firstName(reqDTO.getFirstName()).
                lastName(reqDTO.getLastName()).
                email(reqDTO.getEmail()).
                id(userDetails.getId())).build();

        Optional<User> res = userService.updateUserDetails(userToUpdate);
        if(res==null || res.isEmpty()){
            throw new BadRequestException("Unexpected Exception occured while updating merchantbasic info");
        }
        return res.get();

    }
}
