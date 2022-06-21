package com.example.apiWebFlux.Handler;

import com.example.apiWebFlux.Services.ProductoService;
import com.example.apiWebFlux.models.Producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductorHandler {
    @Autowired
    private ProductoService service;

    public Mono<ServerResponse> listar(ServerRequest request){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(service.findAll(), Producto.class);
    }

}
