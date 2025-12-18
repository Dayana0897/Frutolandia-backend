package com.frutolandia.service;

import com.frutolandia.exception.DuplicateResourceException;
import com.frutolandia.exception.ResourceNotFoundException;
import com.frutolandia.model.User;
import com.frutolandia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para la gestión de usuarios.
 * <p>
 * Proporciona la lógica de negocio para operaciones CRUD de usuarios,
 * incluyendo validaciones de unicidad de email y manejo de transacciones.
 * Interactúa con el repositorio de usuarios para persistir y recuperar datos.
 * </p>
 *
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-04
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /**
     * Crea un nuevo usuario en la base de datos.
     * <p>
     * Verifica que el email no esté duplicado antes de crear el usuario.
     * </p>
     *
     * @param user el usuario a crear
     * @return el usuario creado con su ID generado
     * @throws DuplicateResourceException si el email ya existe
     */
    public User createUser(User user) {
        // Verificar si el email ya existe
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Usuario", "email", user.getEmail());
        }
        return userRepository.save(user);
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id el identificador del usuario
     * @return el usuario encontrado
     * @throws ResourceNotFoundException si el usuario no existe
     */
    @Transactional(readOnly = true)
    public User getUserById(@NonNull Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
    }

    /**
     * Obtiene un usuario por su email.
     *
     * @param email el email del usuario
     * @return el usuario encontrado
     * @throws ResourceNotFoundException si el usuario no existe
     */
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "email", email));
    }

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return lista de todos los usuarios
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Actualiza un usuario existente.
     * <p>
     * Verifica que el nuevo email no esté duplicado si se está cambiando.
     * Solo actualiza los campos que no sean nulos en userDetails.
     * </p>
     *
     * @param id el identificador del usuario a actualizar
     * @param userDetails los nuevos datos del usuario
     * @return el usuario actualizado
     * @throws ResourceNotFoundException si el usuario no existe
     * @throws DuplicateResourceException si el nuevo email ya está en uso
     */
    public User updateUser(@NonNull Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
        
        // Verificar si el nuevo email ya está en uso por otro usuario
        if (userDetails.getEmail() != null && !userDetails.getEmail().equals(user.getEmail())) {
            if (userRepository.findByEmail(userDetails.getEmail()).isPresent()) {
                throw new DuplicateResourceException("Usuario", "email", userDetails.getEmail());
            }
            user.setEmail(userDetails.getEmail());
        }
        
        if (userDetails.getName() != null) {
            user.setName(userDetails.getName());
        }
        if (userDetails.getPassword() != null) {
            user.setPassword(userDetails.getPassword());
        }
        if (userDetails.getRole() != null) {
            user.setRole(userDetails.getRole());
        }
        
        return userRepository.save(user);
    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param id el identificador del usuario a eliminar
     * @throws ResourceNotFoundException si el usuario no existe
     */
    public void deleteUser(@NonNull Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario", "id", id);
        }
        userRepository.deleteById(id);
    }
}
