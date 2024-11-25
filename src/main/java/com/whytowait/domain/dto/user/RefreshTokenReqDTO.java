package com.whytowait.domain.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenReqDTO {
    @NotNull(message = "Refresh Token cannot be empty")
    @NotEmpty(message = "Refresh Token cannot be empty")
    private String refreshToken;
}
