package com.opsera.generator.certificate.exception;

public class InternalServiceException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 6919685621904868774L;

    /**
     * @param message
     */
    public InternalServiceException(String message) {
        super(message);
    }
}
