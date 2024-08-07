package com.babak.springboot.security.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
@ControllerAdvice
public class BaseSecurityExceptionHandler {

    @ExceptionHandler(value = {BaseSecurityException.class})
    public ResponseEntity<ErrorModel> handle(BaseSecurityException exception) {
        ErrorModel errorModel = new ErrorModel(exception);
        return ResponseEntity.status(errorModel.getStatus()).body(errorModel);
    }
}
