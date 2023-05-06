package com.sichizhande.drone.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
public class ErrorDetails {
    private HttpStatus status;
    private String message;
    private Map<String, String> errors;

    public ErrorDetails(HttpStatus status, String message, Map<String, String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
