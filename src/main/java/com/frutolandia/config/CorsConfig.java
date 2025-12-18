package com.frutolandia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de CORS (Cross-Origin Resource Sharing) para la aplicación.
 * <p>
 * Permite que el frontend acceda a la API desde orígenes específicos.
 * Configura los métodos HTTP permitidos, headers y credenciales.
 * </p>
 *
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-04
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    
    /**
     * Configura los mapeos CORS para los endpoints de la API.
     * <p>
     * Permite:
     * <ul>
     *   <li>Acceso desde localhost:5173 y localhost:3000</li>
     *   <li>Métodos: GET, POST, PUT, DELETE, OPTIONS</li>
     *   <li>Todos los headers</li>
     *   <li>Credenciales (cookies, headers de autorización)</li>
     *   <li>Cache de preflight: 3600 segundos (1 hora)</li>
     * </ul>
     * </p>
     *
     * @param registry el registro de configuración CORS
     */
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173", "http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
