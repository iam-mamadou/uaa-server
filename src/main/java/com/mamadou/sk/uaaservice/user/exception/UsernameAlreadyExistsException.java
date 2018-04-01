package com.mamadou.sk.uaaservice.user.exception;


public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }

}
