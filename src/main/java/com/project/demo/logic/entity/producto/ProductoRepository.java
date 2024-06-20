package com.project.demo.logic.entity.producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE %?1%")
    List<Producto> findProductosByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT p FROM Producto p WHERE p.categoria.id = ?1")
    List<Producto> findProductosByCategoriaId(Long categoriaId);

    Optional<Producto> findByNombre(String nombre);
}
