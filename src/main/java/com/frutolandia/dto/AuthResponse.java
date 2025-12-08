package com.frutolandia.dto;

import com.frutolandia.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuesta de autenticación.
 * Contiene el token JWT y la información del usuario.
 * 
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String name;
    private User.Role role;

    public AuthResponse(String token, User user) {
        this.token = token;
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = user.getRole();
    }
}
