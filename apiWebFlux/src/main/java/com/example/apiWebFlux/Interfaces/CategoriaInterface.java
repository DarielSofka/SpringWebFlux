package com.example.apiWebFlux.Interfaces;

import com.example.apiWebFlux.models.Categoria;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoriaInterface extends ReactiveMongoRepository<Categoria, String> {
}
