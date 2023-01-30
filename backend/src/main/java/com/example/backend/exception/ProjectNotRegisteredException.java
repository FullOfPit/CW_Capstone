package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProjectNotRegisteredException extends CustomException{
    public ProjectNotRegisteredException() {
        super("Project not found");
    }
}
