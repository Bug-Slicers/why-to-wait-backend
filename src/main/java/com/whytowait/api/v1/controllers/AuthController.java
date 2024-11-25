package com.whytowait.api.v1.controllers;

import com.whytowait.api.common.exceptions.*;
import com.whytowait.api.common.responses.SuccessResponse;
import com.whytowait.api.v1.services.JwtService;
import com.whytowait.api.v1.services.UserService;
import com.whytowait.domain.dto.user.*;
import com.whytowait.domain.models.User;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @PostMapping(path = "/signup")
    public SuccessResponse<UserRegistrationResponseDTO> registerUser(@Valid @RequestBody UserRegistrationDTO requestBody) throws BadRequestException {
        try {
            User user = UserRegistrationDTO.toUser(requestBody);
            String password = requestBody.getPassword();
            UserRegistrationResponseDTO response = userService.createUser(user, password);
            return new SuccessResponse<UserRegistrationResponseDTO>("User registered successfully", response);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("User already exists");
        }
    }

    @PostMapping(path = "/login")
    public SuccessResponse<UserLoginResDTO> loginUser(@Valid @RequestBody UserLoginReqDTO userLoginDTO) throws BadRequestException, UnauthorizedException {
        UserLoginResDTO response = userService.loginUser(userLoginDTO);
        return new SuccessResponse<UserLoginResDTO>("User Logged in Success", response);
    }

    @PostMapping(path = "/logout")
    public SuccessResponse<String> logoutUser() throws AccessTokenException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDTO userDetails = (UserDetailsDTO) authentication.getPrincipal();
        String response = userService.logoutUser(userDetails.getMobile());
        return new SuccessResponse<String>("User Logout Success", response);
    }

    @PostMapping(path = "/refresh-token")
    public SuccessResponse<UserLoginResDTO> refreshToken(@Valid @RequestBody RefreshTokenReqDTO refreshTokenReqDTO) throws TokenExpiredException, AccessTokenException, BadTokenException {
        UserLoginResDTO resposne = jwtService.validateRefreshTokenAndGenerateAccessTokenAndRefreshToken(refreshTokenReqDTO.getRefreshToken());
        return new SuccessResponse<UserLoginResDTO>("Token Refreshed Successfully", resposne);
    }
}
