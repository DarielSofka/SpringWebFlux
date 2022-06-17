package com.example.prueba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class EjemploFlatMap {

    private static final Logger log = LoggerFactory.getLogger(PruebaApplication.class);

    public static void ejemploFlatMap() throws Exception {

        List<String> usuariosList = new ArrayList<>();
        usuariosList.add("Andres Mengano");
        usuariosList.add("Pedro Sultano");
        usuariosList.add("Maria Fulana");
        usuariosList.add("Diego Fulano");
        usuariosList.add("Juan Perez");
        usuariosList.add("Bruce Willis");
        usuariosList.add("Bruce Lee");

        //de una lista de string a un flux de string
        Flux.fromIterable(usuariosList)
                .map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
                .flatMap(usuario -> {

                    if(usuario.getNombre().equalsIgnoreCase("bruce"))
                        return Mono.just(usuario);

                    return Mono.empty();
                })
                .map(usuario -> {
                    String nombre = usuario.getNombre().toLowerCase();
                    usuario.setNombre(nombre);
                    return usuario;
                })
                .subscribe(u -> log.info(u.toString()));
    }


}
