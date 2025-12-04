package com.frutolandia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO (Data Transfer Object) para respuestas de error estandarizadas.
 * <p>
 * Proporciona una estructura uniforme para todas las respuestas de error
 * de la API, facilitando el manejo de errores en el cliente.
 * </p>
 *
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    /**
     * Fecha y hora en que ocurrió el error.
     */
    private LocalDateTime timestamp;
    
    /**
     * Código de estado HTTP del error.
     */
    private int status;
    
    /**
     * Tipo de error (ej: "Not Found", "Bad Request").
     */
    private String error;
    
    /**
     * Mensaje descriptivo del error.
     */
    private String message;
    
    /**
     * Ruta del endpoint donde ocurrió el error.
     */
    private String path;
    
    /**
     * Mapa de errores de validación (campo -> mensaje de error).
     * Utilizado para errores de validación de formularios.
     */
    private Map<String, String> validationErrors;
    
    /**
     * Constructor para respuestas de error sin errores de validación.
     *
     * @param timestamp fecha y hora del error
     * @param status código de estado HTTP
     * @param error tipo de error
     * @param message mensaje descriptivo
     * @param path ruta del endpoint
     */
    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
