package com.heyya.tools.oauth.google;

public class GoogleAuthFailedException extends RuntimeException {

    public GoogleAuthFailedException() {
    }

    public GoogleAuthFailedException(String message) {
        super(message);
    }
}
