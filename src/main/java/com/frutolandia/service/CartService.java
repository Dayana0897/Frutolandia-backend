package com.frutolandia.service;

import com.frutolandia.dto.CartItemResponse;
import com.frutolandia.model.CartItem;
import com.frutolandia.model.Product;
import com.frutolandia.model.User;
import com.frutolandia.repository.CartItemRepository;
import com.frutolandia.repository.ProductRepository;
import com.frutolandia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión del carrito de compras.
 * 
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-08
 */
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    /**
     * Obtiene todos los ítems del carrito de un usuario.
     */
    public List<CartItemResponse> getCartItems(Long userId) {
        return cartItemRepository.findByUserId(userId).stream()
                .map(item -> new CartItemResponse(
                        item.getId(),
                        item.getProduct(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Añade un producto al carrito o actualiza su cantidad si ya existe.
     */
    @Transactional
    public CartItemResponse addToCart(@NonNull Long userId, @NonNull Long productId, Integer quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Verificar si el ítem ya existe en el carrito
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId)
                .orElse(new CartItem(user, product, 0));

        // Actualizar cantidad
        cartItem.setQuantity(cartItem.getQuantity() + quantity);

        // Guardar
        cartItem = cartItemRepository.save(cartItem);

        return new CartItemResponse(cartItem.getId(), cartItem.getProduct(), cartItem.getQuantity());
    }

    /**
     * Actualiza la cantidad de un ítem en el carrito.
     */
    @Transactional
    public CartItemResponse updateCartItem(@NonNull Long userId, @NonNull Long productId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Ítem no encontrado en el carrito"));

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
            return null;
        }

        cartItem.setQuantity(quantity);
        cartItem = cartItemRepository.save(cartItem);

        return new CartItemResponse(cartItem.getId(), cartItem.getProduct(), cartItem.getQuantity());
    }

    /**
     * Elimina un producto del carrito.
     */
    @Transactional
    public void removeFromCart(Long userId, Long productId) {
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
    }

    /**
     * Vacía el carrito de un usuario.
     */
    @Transactional
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
