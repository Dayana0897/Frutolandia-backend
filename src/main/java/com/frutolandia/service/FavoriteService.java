package com.frutolandia.service;

import com.frutolandia.exception.DuplicateResourceException;
import com.frutolandia.exception.ResourceNotFoundException;
import com.frutolandia.model.Favorite;
import com.frutolandia.model.Product;
import com.frutolandia.model.User;
import com.frutolandia.repository.FavoriteRepository;
import com.frutolandia.repository.ProductRepository;
import com.frutolandia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para la gestión de productos favoritos.
 * 
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-17
 */
@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /**
     * Obtiene todos los productos favoritos de un usuario.
     * 
     * @param email el email del usuario
     * @return lista de productos favoritos
     */
    public List<Product> getFavoritesByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
        
        return favoriteRepository.findByUser(user).stream()
            .map(Favorite::getProduct)
            .toList();
    }

    /**
     * Agrega un producto a los favoritos del usuario.
     * 
     * @param email el email del usuario
     * @param productId el ID del producto
     * @return el producto agregado
     */
    @Transactional
    public Product addFavorite(String email, @NonNull Long productId) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
        
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productId));
        
        // Verificar si ya existe el favorito
        if (favoriteRepository.findByUserIdAndProductId(user.getId(), productId).isPresent()) {
            throw new DuplicateResourceException("El producto ya está en favoritos");
        }
        
        Favorite favorite = new Favorite(user, product);
        favoriteRepository.save(favorite);
        
        return product;
    }

    /**
     * Elimina un producto de los favoritos del usuario.
     * 
     * @param email el email del usuario
     * @param productId el ID del producto
     */
    @Transactional
    public void removeFavorite(String email, @NonNull Long productId) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
        
        Favorite favorite = favoriteRepository.findByUserIdAndProductId(user.getId(), productId)
            .orElseThrow(() -> new ResourceNotFoundException("Favorito no encontrado"));
        
        @SuppressWarnings("null")
        Favorite favoriteToDelete = favorite;
        favoriteRepository.delete(favoriteToDelete);
    }
}
