package com.whytowait.api.v1.services;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.domain.dto.user.UserLoginReqDTO;
import com.whytowait.domain.dto.user.UserLoginResDTO;
import com.whytowait.domain.dto.user.UserRegistrationResponseDTO;
import com.whytowait.domain.models.User;
import com.whytowait.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HashedPasswordService hashedPasswordService;

    @Autowired
    JwtService jwtService;

    @Transactional(rollbackFor = {DataIntegrityViolationException.class})
    public UserRegistrationResponseDTO createUser(User user, String password) throws DataIntegrityViolationException {
        User createdUser = userRepository.save(user);
        hashedPasswordService.createHashedPassword(createdUser, password);
        UserRegistrationResponseDTO userResponse = UserRegistrationResponseDTO.fromUser(createdUser);
        userResponse.setToken(jwtService.generateToken(createdUser.getMobile()));
        return userResponse;
    }

    public UserLoginResDTO loginUser(UserLoginReqDTO userLoginDTO) throws BadRequestException {
        UserLoginResDTO response = new UserLoginResDTO();
        User user = userRepository.findByMobile(userLoginDTO.getMobile());

        if (user == null) {
            throw new BadRequestException("Invalid Credentials");
        }

        if (hashedPasswordService.checkPassword(user.getId(), userLoginDTO.getPassword())) {
            String token = jwtService.generateToken(userLoginDTO.getMobile());
            response.setToken(token);
            response.setUsername(userLoginDTO.getMobile());
            return response;
        } else {
            throw new BadRequestException("Invalid Credentials");
        }
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByMobile(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
