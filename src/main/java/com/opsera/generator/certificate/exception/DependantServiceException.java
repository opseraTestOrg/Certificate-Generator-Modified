package com.opsera.generator.certificate.exception;

public class DependantServiceException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 6919685621904868774L;

    /**
     * @param message
     */
    public DependantServiceException(String message) {
        super(message);
    }
}
