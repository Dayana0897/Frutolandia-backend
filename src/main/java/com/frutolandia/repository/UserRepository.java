package com.frutolandia.repository;

import com.frutolandia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la gestión de usuarios.
 * <p>
 * Proporciona operaciones CRUD básicas heredadas de JpaRepository
 * y métodos personalizados para consultas específicas de usuarios.
 * </p>
 *
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-04
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Busca un usuario por su email.
     * <p>
     * El email es único en el sistema, por lo que devuelve un Optional
     * que contendrá el usuario si existe.
     * </p>
     *
     * @param email el email del usuario a buscar
     * @return Optional con el usuario si existe, vacío en caso contrario
     */
    Optional<User> findByEmail(String email);
}
