package com.project.demo.rest.producto;

import com.project.demo.logic.entity.producto.Producto;
import com.project.demo.logic.entity.producto.ProductoRepository;
import com.project.demo.logic.entity.categoria.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoRestController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'SUPER_ADMIN')")
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Producto addProducto(@RequestBody Producto producto) {
        return categoriaRepository.findById(producto.getCategoria().getId())
                .map(categoria -> {
                    producto.setCategoria(categoria);
                    return productoRepository.save(producto);
                })
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'SUPER_ADMIN')")
    public Producto getProductoById(@PathVariable Long id) {
        return productoRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Producto updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return productoRepository.findById(id)
                .map(existingProducto -> {
                    existingProducto.setNombre(producto.getNombre());
                    existingProducto.setDescripcion(producto.getDescripcion());
                    existingProducto.setPrecio(producto.getPrecio());
                    existingProducto.setCantidadEnStock(producto.getCantidadEnStock());
                    existingProducto.setCategoria(producto.getCategoria());
                    return productoRepository.save(existingProducto);
                })
                .orElseGet(() -> {
                    producto.setId(id);
                    return productoRepository.save(producto);
                });
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void deleteProducto(@PathVariable Long id) {
        productoRepository.deleteById(id);
    }
}
