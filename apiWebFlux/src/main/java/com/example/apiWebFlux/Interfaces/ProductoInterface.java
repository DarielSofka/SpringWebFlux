package com.example.apiWebFlux.Interfaces;

import com.example.apiWebFlux.models.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductoInterface extends ReactiveMongoRepository<Producto, String> {
}
