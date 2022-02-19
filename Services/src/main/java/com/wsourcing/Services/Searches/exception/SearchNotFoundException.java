package com.wsourcing.Services.Searches.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//Exception when the Search is not found meaning that there is no search on this organism
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SearchNotFoundException extends RuntimeException{

    public SearchNotFoundException(String message) {
        super(message);
    }
}
