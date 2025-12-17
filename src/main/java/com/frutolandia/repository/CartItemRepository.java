package com.frutolandia.repository;

import com.frutolandia.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de ítems del carrito.
 * 
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-08
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    /**
     * Encuentra todos los ítems del carrito de un usuario.
     */
    List<CartItem> findByUserId(Long userId);
    
    /**
     * Encuentra un ítem específico del carrito por usuario y producto.
     */
    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);
    
    /**
     * Elimina todos los ítems del carrito de un usuario.
     */
    void deleteByUserId(Long userId);
    
    /**
     * Elimina un ítem específico del carrito.
     */
    void deleteByUserIdAndProductId(Long userId, Long productId);
}
