package com.frutolandia.controller;

import com.frutolandia.model.Product;
import com.frutolandia.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de productos.
 * <p>
 * Proporciona endpoints para operaciones CRUD de productos incluyendo
 * creación, consulta, actualización y eliminación. También incluye
 * funcionalidad de búsqueda por nombre.
 * Los endpoints de modificación requieren rol ADMIN.
 * </p>
 *
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-04
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Crea un nuevo producto.
     * Solo accesible para administradores.
     *
     * @param product el producto a crear (validado)
     * @return ResponseEntity con el producto creado y código HTTP 201 (CREATED)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id el identificador del producto
     * @return ResponseEntity con el producto encontrado y código HTTP 200 (OK)
     * @throws com.frutolandia.exception.ResourceNotFoundException si el producto no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * Obtiene todos los productos disponibles.
     *
     * @return ResponseEntity con la lista de todos los productos y código HTTP 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Busca productos por nombre (búsqueda parcial, no sensible a mayúsculas).
     *
     * @param name el nombre o parte del nombre a buscar
     * @return ResponseEntity con la lista de productos que coinciden con la búsqueda y código HTTP 200 (OK)
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchProductsByName(name));
    }

    /**
     * Actualiza un producto existente.
     * Solo accesible para administradores.
     *
     * @param id el identificador del producto a actualizar
     * @param productDetails los nuevos datos del producto (validados)
     * @return ResponseEntity con el producto actualizado y código HTTP 200 (OK)
     * @throws com.frutolandia.exception.ResourceNotFoundException si el producto no existe
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails) {
        return ResponseEntity.ok(productService.updateProduct(id, productDetails));
    }

    /**
     * Elimina un producto.
     * Solo accesible para administradores.
     *
     * @param id el identificador del producto a eliminar
     * @return ResponseEntity con código HTTP 204 (NO CONTENT)
     * @throws com.frutolandia.exception.ResourceNotFoundException si el producto no existe
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
