package com.whytowait.api.v1.services;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.domain.models.User;
import com.whytowait.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HashedPasswordService hashedPasswordService;

    public User createUser(User user, String password) throws BadRequestException {
        try {
            User createdUser = userRepository.save(user);
            hashedPasswordService.createHashedPassword(createdUser, password);
            return createdUser;
        } catch (DataIntegrityViolationException ex){
            throw new BadRequestException("User already exists");
        }

    }
}
