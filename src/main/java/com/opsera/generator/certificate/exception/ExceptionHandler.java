package com.opsera.generator.certificate.exception;

import com.google.gson.Gson;
import com.opsera.generator.certificate.config.IServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    @Autowired
    private IServiceFactory factory;

    /**
     *
     * Aspect for handling exception
     *
     * @param ex
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(InternalServiceException.class)
    protected ResponseEntity<Object> handleInternalServiceException(InternalServiceException ex) {
        LOGGER.error("InternalServiceException is", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.name());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     *
     * Aspect for handling exception
     *
     * @param ex
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(DependantServiceException.class)
    protected ResponseEntity<Object> handleDependantServiceException(DependantServiceException ex) {
        LOGGER.error("DependantServiceException is", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.name());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     *
     * Handling Server side error while calling other service
     *
     * @param ex
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpServerErrorException.class)
    protected ResponseEntity<Object> handleHttpServerErrorException(HttpServerErrorException ex) {
        LOGGER.error("HttpServerErrorException is", ex);
        String response = ex.getResponseBodyAsString();
        Gson gson = factory.gson();
        ErrorResponse servicenowErrorResponse = gson.fromJson(response, ErrorResponse.class);
        return new ResponseEntity<>(servicenowErrorResponse, ex.getStatusCode());
    }

    /**
     *
     * Handling Server side error while calling other service
     *
     * @param ex
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(HttpServerErrorException ex) {
        LOGGER.error("Exception is", ex);
        String response = ex.getResponseBodyAsString();
        Gson gson = factory.gson();
        ErrorResponse servicenowErrorResponse = gson.fromJson(response, ErrorResponse.class);
        return new ResponseEntity<>(servicenowErrorResponse, ex.getStatusCode());
    }

    /**
     *
     * Handling client side error while calling other service
     *
     * @param ex
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex) {
        LOGGER.error("HttpClientErrorException is", ex);
        String response = ex.getResponseBodyAsString();
        Gson gson = factory.gson();
        ErrorResponse servicenowErrorResponse = gson.fromJson(response, ErrorResponse.class);
        return new ResponseEntity<>(servicenowErrorResponse, ex.getStatusCode());
    }
}
