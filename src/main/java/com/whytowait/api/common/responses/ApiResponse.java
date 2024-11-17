package com.whytowait.api.common.responses;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ApiResponse<T> extends ResponseEntity<Object> {

    protected ApiResponse(HttpStatus status, String message, T data, Object error, HttpHeaders customHeaders){
        super(new ResponseBody<>(message, data, error, LocalDateTime.now()), status);

        if (customHeaders != null) {
            customHeaders.forEach((key, values) -> {
                values.forEach(value -> this.getHeaders().add(key, value));
            });
        }
    }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public record ResponseBody<T>(String message, T data, Object error, LocalDateTime timestamp) {
    }
}
