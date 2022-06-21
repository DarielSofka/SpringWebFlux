package com.example.apiWebFlux.Controllers;


import com.example.apiWebFlux.Services.ProductoService;
import com.example.apiWebFlux.Services.ProductoServiceImpl;
import com.example.apiWebFlux.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductoController {

    @Autowired
    private ProductoService service;

    //Listar todos los productos--------------------
    @GetMapping(value = "/allProducto")
    public Mono<ResponseEntity<Flux<Producto>>> listar(){
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(service.findAll())
        );
    }


    //Listar los productos con ID especifica---------------------------
    @GetMapping("/Producto/{id}")
    public Mono<ResponseEntity<Producto>> ver(@PathVariable("id") String id){
        return service.findById(id).map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //Guardar un Producto---------------------------
    @PostMapping("/addProducto")
    @ResponseStatus(HttpStatus.CREATED)
    private Mono<Producto> save(@RequestBody Producto producto) {
        return this.service.save(producto);
    }

    //Actualizar Producto-----------------------------
    @PutMapping("/updateProducto/{id}")
    public Mono<ResponseEntity<Producto>> editar(@RequestBody Producto producto, @PathVariable String id){
        return service.findById(id).flatMap(p -> {
                    p.setNombre(producto.getNombre());
                    p.setPrecio(producto.getPrecio());
                    p.setCategoria(producto.getCategoria());
                    return service.save(p);
                }).map(p->ResponseEntity.created(URI.create("/updateProducto".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //Eliminar Producto------------------------------
    @DeleteMapping("/removeProducto/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id){
        return service.findById(id).flatMap(p ->{
            return service.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }




}
