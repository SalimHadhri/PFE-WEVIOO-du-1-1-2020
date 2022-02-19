package com.wsourcing.Services.Profiles.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Exception when the Profile is not found
//Profile: Candidates profile found on LINKDIN with the adequate criterias choosen by the RH
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProfileNotFoundException extends RuntimeException{

    public ProfileNotFoundException(String message) {
        super(message);
    }
}
