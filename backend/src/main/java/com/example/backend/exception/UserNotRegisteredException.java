package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotRegisteredException extends CustomException{
    public UserNotRegisteredException() {
        super("This user is not registered");
    }
}
