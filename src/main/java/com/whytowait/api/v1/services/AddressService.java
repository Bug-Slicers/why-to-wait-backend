package com.whytowait.api.v1.services;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.domain.dto.merchant.UpdateMerchantAddressReqDTO;
import com.whytowait.domain.models.Address;
import com.whytowait.domain.models.Merchant;
import com.whytowait.repository.AddressRepository;
import com.whytowait.repository.MerchantRepository;
import com.whytowait.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Transactional
    public Address updateMerchantAddress(UUID merchantID, UpdateMerchantAddressReqDTO updateMerchantAddressReqDTO) throws BadRequestException {
        Optional<Merchant> fetchMerchantByOwnerId = merchantRepository.findById(merchantID);
        Address address = Address.builder().addressLine1(updateMerchantAddressReqDTO.getAddressLine1())
                .addressLine2(updateMerchantAddressReqDTO.getAddressLine2())
                .city(updateMerchantAddressReqDTO.getCity())
                .district(updateMerchantAddressReqDTO.getDistrict())
                .state(updateMerchantAddressReqDTO.getState())
                .pincode(updateMerchantAddressReqDTO.getPincode())
                .build();

        int resMsg = addressRepository.updateAddress(address.getAddressLine1(), address.getAddressLine2(), address.getCity(), address.getDistrict(), address.getState(), address.getPincode(), fetchMerchantByOwnerId.get().getAddress().getId());
        if (resMsg == 0) {
            throw new BadRequestException("Merchant Not Found to update Address");
        }
        
        return address;
    }
}
