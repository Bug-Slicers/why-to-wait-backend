package com.whytowait.domain.dto.merchant;

import lombok.Data;

@Data
public class UpdateMerchantDetailReqDTO {
    private String  firstName;
    private String  lastName;
    private String  email;
}
