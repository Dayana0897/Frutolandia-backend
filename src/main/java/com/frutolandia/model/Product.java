package com.frutolandia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private Double price;

    @Size(max = 500, message = "Los ingredientes no pueden superar los 500 caracteres")
    @Column(columnDefinition = "TEXT")
    private String ingredients;

    @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres")
    @Column(columnDefinition = "TEXT")
    private String description;

    @Min(value = 0, message = "La cantidad en stock no puede ser negativa")
    @Column(name = "stock_quantity")
    private Integer stockQuantity;
}
