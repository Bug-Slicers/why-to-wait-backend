package com.whytowait.api.v1.services;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.domain.models.Address;
import com.whytowait.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Transactional
    Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Transactional
    int updateAddress(Address address, UUID id)throws BadRequestException {
        return addressRepository.updateAddress(address.getAddressLine1(),address.getAddressLine2(),address.getCity(),address.getDistrict(),address.getState(),address.getPincode(),id);
    }
}
