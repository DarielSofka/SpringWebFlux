package com.example.apiWebFlux.Interfaces;

import com.example.apiWebFlux.models.Categoria;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CategoriaInterface extends ReactiveMongoRepository<Categoria, String> {
    public Mono<Categoria> findByNombre(String nombre);
}
