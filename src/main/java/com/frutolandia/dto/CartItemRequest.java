package com.frutolandia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para añadir o actualizar un ítem en el carrito.
 * 
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {
    private Long productId;
    private Integer quantity;
}
