package com.frutolandia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un producto en el sistema Frutolandia.
 * <p>
 * Almacena información sobre productos incluyendo nombre, precio,
 * ingredientes, descripción y cantidad en stock. Incluye validaciones
 * para garantizar la integridad de los datos.
 * </p>
 *
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    /**
     * Identificador único del producto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del producto.
     * <p>
     * Debe tener entre 2 y 100 caracteres y no puede estar vacío.
     * </p>
     */
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(nullable = false)
    private String name;

    /**
     * Precio del producto.
     * <p>
     * Debe ser mayor a 0.
     * </p>
     */
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private Double price;

    /**
     * Ingredientes del producto.
     * <p>
     * Máximo 500 caracteres. Campo opcional.
     * </p>
     */
    @Size(max = 500, message = "Los ingredientes no pueden superar los 500 caracteres")
    @Column(columnDefinition = "TEXT")
    private String ingredients;

    /**
     * Descripción detallada del producto.
     * <p>
     * Máximo 1000 caracteres. Campo opcional.
     * </p>
     */
    @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres")
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Cantidad disponible en stock.
     * <p>
     * No puede ser negativa. Campo opcional.
     * </p>
     */
    @Min(value = 0, message = "La cantidad en stock no puede ser negativa")
    @Column(name = "stock_quantity")
    private Integer stockQuantity;
}
