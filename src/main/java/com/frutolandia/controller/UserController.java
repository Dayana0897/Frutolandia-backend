package com.frutolandia.controller;

import com.frutolandia.model.User;
import com.frutolandia.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de usuarios.
 * <p>
 * Proporciona endpoints para operaciones CRUD de usuarios incluyendo
 * creación, consulta por ID, consulta por email, actualización y eliminación.
 * Incluye validación de duplicados de email.
 * Todos los endpoints requieren rol ADMIN.
 * </p>
 *
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-04
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    /**
     * Crea un nuevo usuario.
     *
     * @param user el usuario a crear (validado)
     * @return ResponseEntity con el usuario creado y código HTTP 201 (CREATED)
     * @throws com.frutolandia.exception.DuplicateResourceException si el email ya existe
     */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id el identificador del usuario
     * @return ResponseEntity con el usuario encontrado y código HTTP 200 (OK)
     * @throws com.frutolandia.exception.ResourceNotFoundException si el usuario no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Obtiene un usuario por su email.
     *
     * @param email el email del usuario
     * @return ResponseEntity con el usuario encontrado y código HTTP 200 (OK)
     * @throws com.frutolandia.exception.ResourceNotFoundException si el usuario no existe
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return ResponseEntity con la lista de todos los usuarios y código HTTP 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id el identificador del usuario a actualizar
     * @param userDetails los nuevos datos del usuario (validados)
     * @return ResponseEntity con el usuario actualizado y código HTTP 200 (OK)
     * @throws com.frutolandia.exception.ResourceNotFoundException si el usuario no existe
     * @throws com.frutolandia.exception.DuplicateResourceException si el nuevo email ya está en uso
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable @NonNull Long id, @RequestBody @Valid User userDetails) {
        return ResponseEntity.ok(userService.updateUser(id, userDetails));
    }

    /**
     * Elimina un usuario.
     *
     * @param id el identificador del usuario a eliminar
     * @return ResponseEntity con código HTTP 204 (NO CONTENT)
     * @throws com.frutolandia.exception.ResourceNotFoundException si el usuario no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @NonNull Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
