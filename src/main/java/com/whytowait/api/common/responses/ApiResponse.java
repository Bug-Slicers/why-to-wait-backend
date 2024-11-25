package com.whytowait.api.common.responses;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ApiResponse<T> extends ResponseEntity<Object> {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
    private final ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build().setDateFormat(df);  // Jackson's ObjectMapper


    protected ApiResponse(HttpStatus status, String message, T data, Object error, HttpHeaders customHeaders) {
        super(new ResponseBody<>(message, data, error, LocalDateTime.now()), customHeaders, status);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ResponseBody<T>(String message, T data, Object error, LocalDateTime timestamp) {
    }

    public void writeToResponse(HttpServletResponse response) throws IOException {
        response.setStatus(this.getStatusCode().value());

        for (String headerName : this.getHeaders().keySet()) {
            for (String headerValue : Objects.requireNonNull(this.getHeaders().get(headerName))) {
                response.addHeader(headerName, headerValue);  // Set header in response
            }
        }

        // Serialize the body object to JSON and write it to the HttpServletResponse
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), this.getBody());
    }
}
