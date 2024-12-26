package com.whytowait.core.annotations.enums;

import lombok.Getter;

@Getter
public enum RequestSource {
    HEADER,
    BODY,
    PARAM
}
