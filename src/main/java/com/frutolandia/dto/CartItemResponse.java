package com.frutolandia.dto;

import com.frutolandia.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para representar un ítem del carrito en las respuestas.
 * 
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Long id;
    private Product product;
    private Integer quantity;
}
