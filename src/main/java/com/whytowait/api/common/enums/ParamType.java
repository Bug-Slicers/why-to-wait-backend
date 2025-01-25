package com.whytowait.api.common.enums;

import com.whytowait.api.common.exceptions.BadRequestException;

public enum ParamType{

    BASIC("basic"),
    BASIC_ADDRESS("basic address"),
    BASIC_TIMING("basic timing"),
    BASIC_ADDRESS_TIMING("basic address timing");

    private final String value;

    ParamType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ParamType fromString(String text) {
        for (ParamType type : ParamType.values()) {
            if (type.value.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }
}
