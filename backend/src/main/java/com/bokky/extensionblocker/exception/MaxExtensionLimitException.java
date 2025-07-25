package com.bokky.extensionblocker.exception;

public class MaxExtensionLimitException extends RuntimeException {
    public MaxExtensionLimitException(String message) {
        super(message);
    }
}
