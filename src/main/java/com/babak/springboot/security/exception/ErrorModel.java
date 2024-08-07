package com.babak.springboot.security.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
public class ErrorModel {

    private String message;
    @JsonIgnore
    private HttpStatus status;

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorModel(BaseSecurityException exception) {
        this.message = exception.getMessage();
        this.status = switch (exception.getCode()) {
            case 1000 -> HttpStatus.UNAUTHORIZED;
            default -> throw new RuntimeException("Invalid security exception code");
        };
    }
}
