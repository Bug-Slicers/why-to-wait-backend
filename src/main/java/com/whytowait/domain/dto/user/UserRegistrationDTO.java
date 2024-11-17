package com.whytowait.domain.dto.user;

import com.whytowait.domain.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationDTO {
    @NotNull(message = "First cannot be empty")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
    private String firstName;

    @NotNull(message = "Last cannot be empty")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
    private String lastName;

    @NotNull(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Mobile cannot be empty")
    @Pattern(
            regexp = "^\\+91[6-9]\\d{9}$",
            message = "Invalid mobile number format. It should start with +91 and contain 10 digits."
    )
    private String mobile;

    @NotNull(message = "Password cannot be empty")
    @Size(min = 6, max = 15, message = "Password length must be between 6 to 15 characters")
    private String password;

    public static User toUser(UserRegistrationDTO data) {
        return User.builder()
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .email(data.getEmail())
                .mobile(data.getEmail())
                .build();
    }
}
