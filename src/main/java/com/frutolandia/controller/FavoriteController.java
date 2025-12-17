package com.frutolandia.controller;

import com.frutolandia.model.Product;
import com.frutolandia.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de productos favoritos.
 * <p>
 * Proporciona endpoints para agregar, eliminar y consultar favoritos.
 * Los endpoints están protegidos y requieren autenticación.
 * </p>
 *
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-17
 */
@RestController
@RequestMapping("/api/users/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    /**
     * Obtiene todos los productos favoritos del usuario autenticado.
     *
     * @param authentication la autenticación del usuario
     * @return ResponseEntity con la lista de productos favoritos
     */
    @GetMapping
    public ResponseEntity<List<Product>> getFavorites(Authentication authentication) {
        String email = authentication.getName();
        List<Product> favorites = favoriteService.getFavoritesByUserEmail(email);
        return ResponseEntity.ok(favorites);
    }

    /**
     * Agrega un producto a los favoritos del usuario autenticado.
     *
     * @param productId el ID del producto a agregar
     * @param authentication la autenticación del usuario
     * @return ResponseEntity con el producto agregado
     */
    @PostMapping("/{productId}")
    public ResponseEntity<Product> addFavorite(
            @PathVariable Long productId,
            Authentication authentication) {
        String email = authentication.getName();
        Product product = favoriteService.addFavorite(email, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    /**
     * Elimina un producto de los favoritos del usuario autenticado.
     *
     * @param productId el ID del producto a eliminar
     * @param authentication la autenticación del usuario
     * @return ResponseEntity vacío con código 204
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeFavorite(
            @PathVariable Long productId,
            Authentication authentication) {
        String email = authentication.getName();
        favoriteService.removeFavorite(email, productId);
        return ResponseEntity.noContent().build();
    }
}
