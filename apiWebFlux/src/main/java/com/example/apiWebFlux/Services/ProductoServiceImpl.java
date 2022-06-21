package com.example.apiWebFlux.Services;

import com.example.apiWebFlux.Interfaces.CategoriaInterface;
import com.example.apiWebFlux.Interfaces.ProductoInterface;
import com.example.apiWebFlux.models.Categoria;
import com.example.apiWebFlux.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    ProductoInterface productoInterface;

    @Autowired
    CategoriaInterface categoriaInterface;

    @Override
    public Flux<Producto> findAll() {
        return productoInterface.findAll();
    }

    @Override
    public Mono<Producto> findById(String id) {
        return productoInterface.findById(id);
    }

    @Override
    public Mono<Producto> save(Producto producto) {
        return productoInterface.save(producto);
    }

    @Override
    public Mono<Void> delete(Producto producto) {
        return productoInterface.delete(producto);
    }

    @Override
    public Flux<Producto> findAllConNombreUpperCase() {
        return productoInterface.findAll().map(producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        });
    }

    @Override
    public Flux<Producto> findAllConNombreUpperCaseRepeat() {
        return findAllConNombreUpperCase().repeat(5000);
    }

    @Override
    public Flux<Categoria> findAllCategoria() {
        return categoriaInterface.findAll();
    }

    @Override
    public Mono<Categoria> findCategoriaById(String id) {
        return categoriaInterface.findById(id);
    }

    @Override
    public Mono<Categoria> saveCategoria(Categoria categoria) {
        return categoriaInterface.save(categoria);
    }
}
