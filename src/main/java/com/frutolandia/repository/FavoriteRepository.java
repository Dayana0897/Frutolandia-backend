package com.frutolandia.repository;

import com.frutolandia.model.Favorite;
import com.frutolandia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de favoritos.
 * 
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-17
 */
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    /**
     * Busca todos los favoritos de un usuario.
     * 
     * @param user el usuario
     * @return lista de favoritos del usuario
     */
    List<Favorite> findByUser(User user);

    /**
     * Busca un favorito específico por usuario y producto.
     * 
     * @param userId el ID del usuario
     * @param productId el ID del producto
     * @return Optional con el favorito si existe
     */
    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId AND f.product.id = :productId")
    Optional<Favorite> findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    /**
     * Elimina un favorito por usuario y producto.
     * 
     * @param userId el ID del usuario
     * @param productId el ID del producto
     */
    void deleteByUserIdAndProductId(Long userId, Long productId);
    
    /**
     * Elimina todos los favoritos de un usuario.
     * 
     * @param userId el ID del usuario
     */
    void deleteByUserId(Long userId);
    
    /**
     * Elimina todos los favoritos de un producto.
     * 
     * @param productId el ID del producto
     */
    void deleteByProductId(Long productId);
}
