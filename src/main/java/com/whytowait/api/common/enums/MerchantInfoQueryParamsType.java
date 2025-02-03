package com.whytowait.api.common.enums;

public enum MerchantInfoQueryParamsType {

    BASIC("basic"),
    BASIC_ADDRESS("basic address"),
    BASIC_TIMING("basic timing"),
    BASIC_ADDRESS_TIMING("basic address timing");

    private final String value;

    MerchantInfoQueryParamsType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MerchantInfoQueryParamsType fromString(String text) {
        for (MerchantInfoQueryParamsType type : MerchantInfoQueryParamsType.values()) {
            if (type.value.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }
}
