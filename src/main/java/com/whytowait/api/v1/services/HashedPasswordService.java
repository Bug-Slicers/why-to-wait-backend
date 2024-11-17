package com.whytowait.api.v1.services;

import com.whytowait.domain.models.HashedPassword;
import com.whytowait.domain.models.User;
import com.whytowait.repository.HashedPasswordRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HashedPasswordService {

    @Autowired
    HashedPasswordRepository hashedPasswordRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    void createHashedPassword(User user, String password){
        UUID userId = user.getId();
        HashedPassword hashedPassword = HashedPassword.builder().userId(userId).hashedPassword(password).build();
        hashedPasswordRepository.save(hashedPassword);
    }

}
