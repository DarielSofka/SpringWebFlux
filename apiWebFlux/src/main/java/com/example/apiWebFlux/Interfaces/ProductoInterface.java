package com.example.apiWebFlux.Interfaces;

import com.example.apiWebFlux.models.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ProductoInterface extends ReactiveMongoRepository<Producto, String> {
    public Mono<Producto> findByNombre(String nombre);
}
