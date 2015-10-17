package com.raulvintila.app.lieflashcards.SyncClasses;

import com.raulvintila.app.lieflashcards.SyncClasses.Payload;

public class RequestReturnedMessage {
    private Number error;
    private String message;
    private Payload payload;
    private Boolean failed;

    public void setFailed(Boolean failed) {
        this.failed = failed;
    }

    public Boolean getFailed() {
        return this.failed;
    }

    public void setError(Number error) {
        this.error = error;
    }

    public Number getError() {
        return this.error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Payload getPayload() {
        return this.payload;
    }
}
