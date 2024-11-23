package com.whytowait.api.v1.services;

import com.whytowait.domain.dto.user.FetchUserDTO;
import com.whytowait.repository.HashedPasswordRepository;
import com.whytowait.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HashedPasswordRepository hashedPasswordRepository;

    public FetchUserDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        FetchUserDTO user =userRepository.findUserByMobileWithUserBody(username);
        if(user==null){
            throw new UsernameNotFoundException("user not found");
        }
        return user;
    }

}
