package com.whytowait.api.v1.controllers;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.api.common.responses.SuccessResponse;
import com.whytowait.api.v1.services.UserService;
import com.whytowait.domain.dto.user.UserRegistrationDTO;
import com.whytowait.domain.dto.user.UserRegistrationResponseDTO;
import com.whytowait.domain.models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public SuccessResponse<UserRegistrationResponseDTO> registerUser(@Valid @RequestBody UserRegistrationDTO requestBody) throws BadRequestException {
        User user = UserRegistrationDTO.toUser(requestBody);
        String password = requesstBody.getPassword();
        User createdUser = userService.createUser(user, password);
        UserRegistrationResponseDTO response = UserRegistrationResponseDTO.fromUser(createdUser);
        return new SuccessResponse<UserRegistrationResponseDTO>("User registered successfully", response);
    }
}
