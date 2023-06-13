package com.heyya.tools.oauth.apple;

public class AppleAuthFailedException extends RuntimeException {

    public AppleAuthFailedException() {
    }

    public AppleAuthFailedException(String message) {
        super(message);
    }
}
