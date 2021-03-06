package com.example.prueba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class EjemploIterable {

    private static final Logger log = LoggerFactory.getLogger(PruebaApplication.class);


    public static void ejemploIterable() throws Exception {

        List<String> usuariosList = new ArrayList<>();
        usuariosList.add("Andres Mengano");
        usuariosList.add("Pedro Sultano");
        usuariosList.add("Maria Fulana");
        usuariosList.add("Diego Fulano");
        usuariosList.add("Juan Perez");
        usuariosList.add("Bruce Willis");
        usuariosList.add("Bruce Lee");

        //de una lista de string a un flux de string
        Flux<String> nombres = Flux.fromIterable(usuariosList);

        //Los observadores son inmutables, por cada operacion que hagamos a un flujo, este abrira una nueva instancia
        Flux<Usuario> usuarios = nombres.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
                .filter(usuario -> usuario.getNombre().equalsIgnoreCase("bruce"))
                .doOnNext(usuario -> {
                    if (usuario == null)
                        throw new RuntimeException("Nombres no pueden ser vacios");
                    System.out.println(usuario.getNombre().concat(" ").concat(usuario.getApellido()));
                })
                .map(usuario -> {
                    String nombre = usuario.getNombre().toLowerCase();
                    usuario.setNombre(nombre);
                    return usuario;
                });

        usuarios.subscribe(e -> log.info(e.toString()),
                error -> log.error(error.getMessage()),
                new Runnable() {
                    @Override
                    public void run() {
                        log.info("Ha finalizado la ejecucion del observable con exito!");
                    }
                });
    }

}
