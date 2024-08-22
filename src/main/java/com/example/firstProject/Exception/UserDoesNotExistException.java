package com.example.firstProject.Exception;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserDoesNotExistException extends RuntimeException{
    public UserDoesNotExistException(long userId){
        super("Meeting " + userId + " does not exist.");
    }
}