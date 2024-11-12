package com.si2001.webapp.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)  // Imposta il codice di stato HTTP 404
public class PrenotazioneNotFoundException extends RuntimeException {

    public PrenotazioneNotFoundException(String message) {
        super(message);
    }
}
