package com.whytowait.domain.dto.user;

import com.whytowait.api.v1.services.JwtService;
import com.whytowait.domain.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationResponseDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String token;
    private String refreshToken;

    public static UserRegistrationResponseDTO fromUser(User user) {
        return UserRegistrationResponseDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .build();
    }
}
