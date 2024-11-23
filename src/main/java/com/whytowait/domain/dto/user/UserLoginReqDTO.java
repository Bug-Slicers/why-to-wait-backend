package com.whytowait.domain.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginReqDTO {

    @NotNull(message = "Mobile cannot be empty")
    @Pattern(
            regexp = "^\\+91[6-9]\\d{9}$",
            message = "Invalid mobile number format. It should start with +91 and contain 10 digits."
    )
    private String mobile;

    @NotNull(message = "Password cannot be empty")
    @Size(min = 6, max = 15, message = "Password length must be between 6 to 15 characters")
    private String password;
}
