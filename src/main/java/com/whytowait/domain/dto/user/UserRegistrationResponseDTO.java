package com.whytowait.domain.dto.user;

import com.whytowait.domain.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationResponseDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;

    public static UserRegistrationResponseDTO fromUser(User user) {
        return UserRegistrationResponseDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .build();
    }
}
