package com.si2001.webapp.response;

import java.util.Map;

public class PrenotazioneResponse {

    private boolean validated; 
    private String successMessage; 
    private Map<String, String> errorMessages; 

    // Getters e Setters

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public Map<String, String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
