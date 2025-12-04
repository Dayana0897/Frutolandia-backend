package com.frutolandia.service;

import com.frutolandia.exception.ResourceNotFoundException;
import com.frutolandia.model.Product;
import com.frutolandia.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para la gestión de productos.
 * <p>
 * Proporciona la lógica de negocio para operaciones CRUD de productos,
 * incluyendo validaciones y manejo de transacciones. Interactúa con el
 * repositorio de productos para persistir y recuperar datos.
 * </p>
 *
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-04
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Crea un nuevo producto en la base de datos.
     *
     * @param product el producto a crear
     * @return el producto creado con su ID generado
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id el identificador del producto
     * @return el producto encontrado
     * @throws ResourceNotFoundException si el producto no existe
     */
    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
    }

    /**
     * Obtiene todos los productos disponibles.
     *
     * @return lista de todos los productos
     */
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Busca productos por nombre (búsqueda parcial, no sensible a mayúsculas).
     *
     * @param name el nombre o parte del nombre a buscar
     * @return lista de productos que coinciden con la búsqueda
     */
    @Transactional(readOnly = true)
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Actualiza un producto existente.
     * <p>
     * Solo actualiza los campos que no sean nulos en productDetails.
     * </p>
     *
     * @param id el identificador del producto a actualizar
     * @param productDetails los nuevos datos del producto
     * @return el producto actualizado
     * @throws ResourceNotFoundException si el producto no existe
     */
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
        
        if (productDetails.getName() != null) {
            product.setName(productDetails.getName());
        }
        if (productDetails.getPrice() != null) {
            product.setPrice(productDetails.getPrice());
        }
        if (productDetails.getIngredients() != null) {
            product.setIngredients(productDetails.getIngredients());
        }
        if (productDetails.getDescription() != null) {
            product.setDescription(productDetails.getDescription());
        }
        if (productDetails.getStockQuantity() != null) {
            product.setStockQuantity(productDetails.getStockQuantity());
        }
        
        return productRepository.save(product);
    }

    /**
     * Elimina un producto de la base de datos.
     *
     * @param id el identificador del producto a eliminar
     * @throws ResourceNotFoundException si el producto no existe
     */
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto", "id", id);
        }
        productRepository.deleteById(id);
    }
}
