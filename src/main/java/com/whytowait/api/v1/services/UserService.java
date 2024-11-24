package com.whytowait.api.v1.services;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.domain.dto.user.UserLoginReqDTO;
import com.whytowait.domain.dto.user.UserLoginResDTO;
import com.whytowait.domain.models.User;
import com.whytowait.repository.HashedPasswordRepository;
import com.whytowait.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HashedPasswordRepository hashedPasswordRepository;

    @Autowired
    HashedPasswordService hashedPasswordService;

    @Autowired
    JwtService jwtService;

    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();


    @Transactional
    public User createUser(User user, String password) throws BadRequestException {
        try {
            User createdUser = userRepository.save(user);
            hashedPasswordService.createHashedPassword(createdUser, password);
            return createdUser;
        } catch (DataIntegrityViolationException ex){
            throw new BadRequestException("User already exists");
        }

    }

    public UserLoginResDTO loginUser(UserLoginReqDTO userLoginDTO) throws BadRequestException{

        UserLoginResDTO respone = new UserLoginResDTO();
        UUID userId;
        userId = userRepository.findUserByMobile(userLoginDTO.getMobile());
        if(userId==null){
            throw new BadRequestException("Invalid Credentials");
        }

        try{
            String dbUserPassword= hashedPasswordRepository.findPasswordByUserId(userId);
            if(bcrypt.matches(userLoginDTO.getPassword(),dbUserPassword)){
                String token = jwtService.generateToken(userLoginDTO.getMobile());
                respone.setToken(token);
                respone.setUsername(userLoginDTO.getMobile());
                return  respone;
            }
            else{
                throw new BadRequestException("Invalid Credentials");
            }
        }
        catch (DataIntegrityViolationException ex){
            throw new BadRequestException("Invalid Credentials");
        }

    }

    @Transactional
    public String logoutUser(String username) throws BadRequestException{
        Integer updateStatus =userRepository.updateLastLogoutTimestamp(username);
        if(updateStatus==1){
            return  "Logout Successful";
        }
        return "Logout Failed";
    }
}
