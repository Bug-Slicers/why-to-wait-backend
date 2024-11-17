package com.whytowait.api.common.responses;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class TokenExpiredResponse<T> extends ApiResponse<T>{
    public TokenExpiredResponse(String message) {
        super(HttpStatus.UNAUTHORIZED, message, null, null, new HttpHeaders() {{
            set("instruction", "refresh_token");
        }});
    }

    public TokenExpiredResponse() {
        super(HttpStatus.UNAUTHORIZED, "Token Expired", null, null, new HttpHeaders() {{
            set("instruction", "refresh_token");
        }});
    }

}
