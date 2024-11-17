package com.whytowait.api.v1.services;

import com.whytowait.domain.models.User;
import com.whytowait.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HashedPasswordService hashedPasswordService;

    public User createUser(User user, String password){
       User createdUser = userRepository.save(user);
       hashedPasswordService.createHashedPassword(createdUser, password);
       return createdUser;
    }
}
