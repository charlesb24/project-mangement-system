package com.example.charlesb.projectmanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ManagerException extends RuntimeException {
    public ManagerException(String message) {
        super(message);
    }
}
