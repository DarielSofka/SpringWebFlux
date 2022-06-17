package com.example.prueba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class EjemploCollectList {

    private static final Logger log = LoggerFactory.getLogger(PruebaApplication.class);

    public static void ejemploCollectList() throws Exception {

        List<Usuario> usuariosList = new ArrayList<>();
        usuariosList.add(new Usuario("Andres","Mengano"));
        usuariosList.add(new Usuario("Pedro","Sultano"));
        usuariosList.add(new Usuario("Maria","Fulana"));
        usuariosList.add(new Usuario("Diego","Fulano"));
        usuariosList.add(new Usuario("Juan","Perez"));
        usuariosList.add(new Usuario("Bruce","Willis"));
        usuariosList.add(new Usuario("Bruce","Lee"));

        Flux.fromIterable(usuariosList)
                .collectList()
                .subscribe(lista -> {
                    lista.forEach(item -> log.info(item.toString()));
                });
    }




}
