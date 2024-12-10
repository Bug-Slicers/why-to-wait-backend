package com.whytowait.core.annotations.enums;

import lombok.Getter;

@Getter
public enum RequestSource {
    HEADER("merchant-id"),
    PARAMS("merchantId"),
    BODY("merchantId");

    private final String source;

    RequestSource(String source) {
        this.source = source;
    }
}
