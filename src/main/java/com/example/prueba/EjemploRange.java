package com.example.prueba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class EjemploRange {

    private static final Logger log = LoggerFactory.getLogger(PruebaApplication.class);

    public static void ejemploRange() throws Exception {
        Flux<Integer> rango = Flux.range(0,4);
        Flux.just(1, 2, 3, 4)
                .map(i -> (i * 2))
                .zipWith(rango, (uno, dos) -> String.format("Primer Flux: %d, Segundo Flux %d", uno, dos))
                .subscribe(texto -> log.info(texto));

    }


}
