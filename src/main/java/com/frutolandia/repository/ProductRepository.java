package com.frutolandia.repository;

import com.frutolandia.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la gestión de productos.
 * <p>
 * Proporciona operaciones CRUD básicas heredadas de JpaRepository
 * y métodos personalizados para consultas específicas de productos.
 * </p>
 *
 * @author Frutolandia Team
 * @version 1.0
 * @since 2025-12-04
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Busca productos cuyo nombre contenga el texto especificado.
     * <p>
     * La búsqueda es insensible a mayúsculas/minúsculas y parcial.
     * </p>
     *
     * @param name el texto a buscar en el nombre del producto
     * @return lista de productos que coinciden con la búsqueda
     */
    List<Product> findByNameContainingIgnoreCase(String name);
}
