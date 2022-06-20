package com.example.prueba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.example.prueba.EjemploCollectList.ejemploCollectList;
import static com.example.prueba.EjemploFlatMap.ejemploFlatMap;
import static com.example.prueba.EjemploIntervalo.ejemploDelayElements;
import static com.example.prueba.EjemploIntervalo.ejemploIntervaloInfinito;
import static com.example.prueba.EjemploIterable.ejemploIterable;
import static com.example.prueba.EjemploRange.ejemploRange;
import static com.example.prueba.EjemploToString.ejemploToString;

@SpringBootApplication
public class PruebaApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(PruebaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PruebaApplication.class, args);
    }

    public void run(String... args) throws Exception {
        //ejemploIterable();
        //ejemploFlatMap();
        //ejemploToString();
        //ejemploCollectList();
        //ejemploUsuarioComentarioFlatMap();
        //ejemploUsuarioComentarioZipWith();
        //ejemploUsuarioComentarioZipWithForma2();
        //ejemploRange();
        //ejemploDelayElements();
        ejemploIntervaloInfinito();
    }

    public void ejemploUsuarioComentarioZipWithForma2() {
        Mono<Usuario> usuarioMono = Mono.fromCallable(() -> new Usuario("Jhon", "Doe"));

        Mono<Comentarios> comentariosUsuarioMono = Mono.fromCallable(() -> {
            Comentarios comentarios = new Comentarios();
            comentarios.addComentarios("Hola pepe, qué tal!");
            comentarios.addComentarios("Mañana voy a la playa");
            comentarios.addComentarios("Estoy tomando el curso de spring con reactor");
            return comentarios;
        });

        //Combinar 2 flujos
        Mono<UsuarioComentario> usuarioConComentarios = usuarioMono
                .zipWith(comentariosUsuarioMono)
                .map(tuple -> {
                    Usuario u = tuple.getT1();
                    Comentarios c = tuple.getT2();
                    return new UsuarioComentario(u, c);
                });

        usuarioConComentarios.subscribe(uc -> log.info(uc.toString()));
    }

    public void ejemploUsuarioComentarioZipWith() {
        Mono<Usuario> usuarioMono = Mono.fromCallable(() -> new Usuario("Jhon", "Doe"));

        Mono<Comentarios> comentariosUsuarioMono = Mono.fromCallable(() -> {
            Comentarios comentarios = new Comentarios();
            comentarios.addComentarios("Hola pepe, qué tal!");
            comentarios.addComentarios("Mañana voy a la playa");
            comentarios.addComentarios("Estoy tomando el curso de spring con reactor");
            return comentarios;
        });

        //Combinar 2 flujos
        Mono<UsuarioComentario> usuarioConComentarios = usuarioMono
                .zipWith(comentariosUsuarioMono, (usuario, comentariosUsuario) -> new UsuarioComentario(usuario, comentariosUsuario));

        usuarioConComentarios.subscribe(uc -> log.info(uc.toString()));
    }

    public void ejemploUsuarioComentarioFlatMap() {
        Mono<Usuario> usuarioMono = Mono.fromCallable(() -> new Usuario("Jhon", "Doe"));

        Mono<Comentarios> comentariosUsuarioMono = Mono.fromCallable(() -> {
            Comentarios comentarios = new Comentarios();
            comentarios.addComentarios("Hola pepe, qué tal!");
            comentarios.addComentarios("Mañana voy a la playa");
            comentarios.addComentarios("Estoy tomando el curso de spring con reactor");
            return comentarios;
        });

        usuarioMono.flatMap(u -> comentariosUsuarioMono.map(c -> new UsuarioComentario(u, c)))
                .subscribe(uc -> log.info(uc.toString()));
    }

}
