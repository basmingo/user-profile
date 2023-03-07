package com.iprody.user.profile.exception;

/**
 * Thrown when object processing is impossible.
 */
public class ResourceProcessingException extends RuntimeException {

    /**
     * @param message description of exception.
     */
    public ResourceProcessingException(final String message) {
        super(message);
    }

    /**
     * @param message description of exception.
     * @param cause   throwable object.
     */
    public ResourceProcessingException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
