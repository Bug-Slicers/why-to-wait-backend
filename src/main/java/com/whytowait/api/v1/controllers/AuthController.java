package com.whytowait.api.v1.controllers;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.api.common.responses.SuccessResponse;
import com.whytowait.api.v1.services.UserService;
import com.whytowait.domain.dto.user.UserLoginReqDTO;
import com.whytowait.domain.dto.user.UserLoginResDTO;
import com.whytowait.domain.dto.user.UserRegistrationDTO;
import com.whytowait.domain.dto.user.UserRegistrationResponseDTO;
import com.whytowait.domain.models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

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
    public SuccessResponse<UserLoginResDTO> loginUser(@RequestBody UserLoginReqDTO userLoginDTO) throws BadRequestException {
        UserLoginResDTO response = userService.loginUser(userLoginDTO);
        return new SuccessResponse<UserLoginResDTO>("User Loggedin Success", response);
    }

    @GetMapping
    public String getString() {
        return "hii";
    }
}
