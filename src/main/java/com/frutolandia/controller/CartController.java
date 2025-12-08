package com.frutolandia.controller;

import com.frutolandia.dto.CartItemRequest;
import com.frutolandia.dto.CartItemResponse;
import com.frutolandia.security.JwtUtil;
import com.frutolandia.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para la gestión del carrito de compras.
 * 
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-08
 */
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final JwtUtil jwtUtil;

    /**
     * Extrae el ID del usuario desde el token JWT.
     */
    private Long getUserIdFromToken(String authHeader) {
        String token = authHeader.substring(7); // Remover "Bearer "
        return jwtUtil.extractUserId(token);
    }

    /**
     * Obtiene todos los ítems del carrito del usuario autenticado.
     */
    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCart(
            @RequestHeader("Authorization") String authHeader) {
        try {
            Long userId = getUserIdFromToken(authHeader);
            List<CartItemResponse> items = cartService.getCartItems(userId);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Añade un producto al carrito.
     */
    @PostMapping
    public ResponseEntity<?> addToCart(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CartItemRequest request) {
        try {
            Long userId = getUserIdFromToken(authHeader);
            CartItemResponse item = cartService.addToCart(
                    userId, 
                    request.getProductId(), 
                    request.getQuantity()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(item);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error al añadir al carrito: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Actualiza la cantidad de un ítem en el carrito.
     */
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateCartItem(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long productId,
            @RequestBody CartItemRequest request) {
        try {
            Long userId = getUserIdFromToken(authHeader);
            CartItemResponse item = cartService.updateCartItem(
                    userId, 
                    productId, 
                    request.getQuantity()
            );
            
            if (item == null) {
                return ResponseEntity.noContent().build();
            }
            
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error al actualizar carrito: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Elimina un producto del carrito.
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> removeFromCart(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long productId) {
        try {
            Long userId = getUserIdFromToken(authHeader);
            cartService.removeFromCart(userId, productId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error al eliminar del carrito: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Vacía el carrito del usuario.
     */
    @DeleteMapping
    public ResponseEntity<?> clearCart(
            @RequestHeader("Authorization") String authHeader) {
        try {
            Long userId = getUserIdFromToken(authHeader);
            cartService.clearCart(userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error al vaciar carrito: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
