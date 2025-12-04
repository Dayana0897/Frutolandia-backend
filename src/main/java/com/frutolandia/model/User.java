package com.frutolandia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un usuario en el sistema Frutolandia.
 * <p>
 * Almacena información sobre usuarios incluyendo credenciales,
 * datos personales y rol. Incluye validaciones para garantizar
 * la integridad de los datos.
 * </p>
 *
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Email del usuario (debe ser único).
     * <p>
     * Debe ser un email válido y no puede estar vacío.
     * </p>
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe proporcionar un email válido")
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Contraseña del usuario.
     * <p>
     * Debe tener al menos 6 caracteres.
     * </p>
     */
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Column(nullable = false)
    private String password;

    /**
     * Nombre completo del usuario.
     * <p>
     * Debe tener entre 2 y 100 caracteres.
     * </p>
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(nullable = false)
    private String name;

    /**
     * Rol del usuario en el sistema.
     */
    @NotNull(message = "El rol es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /**
     * Enumeración de roles disponibles en el sistema.
     * <p>
     * USER: Usuario regular con permisos básicos.<br>
     * ADMIN: Administrador con permisos completos.
     * </p>
     */
    public enum Role {
        /** Usuario regular */
        USER,
        /** Administrador */
        ADMIN
    }
}
