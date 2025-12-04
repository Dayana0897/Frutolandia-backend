package com.frutolandia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Frutolandia Backend.
 * <p>
 * Esta clase contiene el punto de entrada de la aplicación Spring Boot.
 * Configura y arranca el contexto de la aplicación con todas las
 * configuraciones automáticas de Spring Boot.
 * </p>
 *
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-04
 */
@SpringBootApplication
public class FrutolandiaBackendApplication {

    /**
     * Método principal que arranca la aplicación Spring Boot.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(FrutolandiaBackendApplication.class, args);
    }

}
