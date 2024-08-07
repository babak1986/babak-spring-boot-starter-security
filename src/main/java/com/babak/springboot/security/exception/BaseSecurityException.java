package com.babak.springboot.security.exception;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
public abstract class BaseSecurityException extends RuntimeException {

    private String message;
    private int code;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BaseSecurityException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public static class InvalidCredentialsException extends BaseSecurityException {

        public InvalidCredentialsException() {
            super("Invalid credentials", 1000);
        }
    }
}
