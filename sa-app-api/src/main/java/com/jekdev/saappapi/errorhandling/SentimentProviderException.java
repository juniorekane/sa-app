package com.jekdev.saappapi.errorhandling;

public class SentimentProviderException extends RuntimeException {
    public SentimentProviderException(String message) {
        super(message);
    }

    public SentimentProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
