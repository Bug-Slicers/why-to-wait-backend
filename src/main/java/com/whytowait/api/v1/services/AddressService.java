package com.whytowait.api.v1.services;

import com.whytowait.domain.models.Address;
import com.whytowait.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Transactional
    Address createAddress(Address address) {
        return addressRepository.save(address);
    }
}
