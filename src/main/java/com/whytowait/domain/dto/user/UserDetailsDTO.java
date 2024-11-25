package com.whytowait.domain.dto.user;

import com.whytowait.domain.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;

    public static UserDetailsDTO fromUser(User user) {
        return UserDetailsDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .build();
    }
}
