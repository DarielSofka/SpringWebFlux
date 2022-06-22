package com.example.apiWebFlux.Handler;

import com.example.apiWebFlux.Services.ProductoService;
import com.example.apiWebFlux.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import static org.springframework.web.reactive.function.BodyInserters.*;

import java.net.URI;
import java.util.Date;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProductorHandler {
    @Autowired
    private ProductoService service;

    //Listar todos los Productos----------------------------
    public Mono<ServerResponse> listar(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(service.findAll(), Producto.class);
    }

    //Buscar Producto por ID-----------------------------------
    public Mono<ServerResponse> ver(ServerRequest request) {

        String id = request.pathVariable("id");
        return service.findById(id).flatMap(p -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(fromObject(p)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    //Crear un Producto---------------------------------------
    public Mono<ServerResponse> crear(ServerRequest request){
        Mono<Producto> producto = request.bodyToMono(Producto.class);

        return producto.flatMap(p -> {
            if(p.getCreateAt() ==null) {
                p.setCreateAt(new Date());
            }
            return service.save(p);
        }).flatMap(p -> ServerResponse
                .created(URI.create("/api/v2/productos/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(fromObject(p)));
    }

    //Actualizar Producto------------------------------------
    public Mono<ServerResponse> editar(ServerRequest request){
        Mono<Producto> producto = request.bodyToMono(Producto.class);
        String id = request.pathVariable("id");

        Mono<Producto> productoDb = service.findById(id);

        return productoDb.zipWith(producto, (db, req) ->{
                    db.setNombre(req.getNombre());
                    db.setPrecio(req.getPrecio());
                    db.setCategoria(req.getCategoria());
                    return db;
                }).flatMap(p -> ServerResponse.created(URI.create("/api/v2/productos/".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(service.save(p), Producto.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    //Eliminar Producto------------------------------
    public Mono<ServerResponse> eliminar(ServerRequest request){
        String id = request.pathVariable("id");

        Mono<Producto> productoDb = service.findById(id);

        return productoDb.flatMap(p-> service.delete(p).then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

}
