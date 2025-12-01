package com.frutolandia.exception;

/**
 * Excepción personalizada para peticiones incorrectas
 */
public class BadRequestException extends RuntimeException {
    
    public BadRequestException(String message) {
        super(message);
    }
}
