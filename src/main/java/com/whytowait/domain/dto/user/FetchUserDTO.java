package com.whytowait.domain.dto.user;

import java.util.UUID;

public interface FetchUserDTO {
    UUID getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getMobile();
}
