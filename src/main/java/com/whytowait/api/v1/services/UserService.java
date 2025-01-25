package com.whytowait.api.v1.services;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.api.common.exceptions.UnauthorizedException;
import com.whytowait.domain.dto.user.UserLoginReqDTO;
import com.whytowait.domain.dto.user.UserLoginResDTO;
import com.whytowait.domain.dto.user.UserRegistrationResponseDTO;
import com.whytowait.domain.models.MerchantManager;
import com.whytowait.domain.models.User;
import com.whytowait.repository.MerchantManagerRepository;
import com.whytowait.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HashedPasswordService hashedPasswordService;

    @Autowired
    JwtService jwtService;

    @Autowired
    MerchantManagerRepository merchantManagerRepository;

    @Transactional(rollbackFor = {DataIntegrityViolationException.class})
    public UserRegistrationResponseDTO createUser(User user, String password) throws DataIntegrityViolationException {
        User createdUser = userRepository.save(user);
        hashedPasswordService.createHashedPassword(createdUser, password);
        UserRegistrationResponseDTO userResponse = UserRegistrationResponseDTO.fromUser(createdUser);
        userResponse.setToken(jwtService.generateToken(createdUser.getMobile(), createdUser.getRole().name(), new ArrayList<>()));
        userResponse.setRefreshToken(jwtService.generateRefreshToken(createdUser.getMobile()));
        return userResponse;
    }

    public UserLoginResDTO loginUser(UserLoginReqDTO userLoginDTO) throws BadRequestException, UnauthorizedException {
        UserLoginResDTO response = new UserLoginResDTO();
        User user = userRepository.findByMobile(userLoginDTO.getMobile());

        if (user == null) {
            throw new UnauthorizedException("Invalid Credentials");
        }

        List<MerchantManager> merchantRoles = merchantManagerRepository.findByUserId(user.getId());

        if (hashedPasswordService.checkPassword(user.getId(), userLoginDTO.getPassword())) {
            String token = jwtService.generateToken(user.getMobile(), user.getRole().name(), merchantRoles);
            String refreshToken = jwtService.generateRefreshToken(user.getMobile());
            response.setToken(token);
            response.setRefreshToken(refreshToken);
            response.setUsername(userLoginDTO.getMobile());
            return response;
        } else {
            throw new UnauthorizedException("Invalid Credentials");
        }
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByMobile(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Transactional
    public String logoutUser(String mobile) {
        Integer updateStatus = userRepository.updateLastLogoutTimestamp(mobile);
        if (updateStatus == 1) {
            return "Logout Successful";
        }
        return "Logout Failed";
    }

}
