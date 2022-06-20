package com.example.prueba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class EjemploIntervalo {

    private static final Logger log = LoggerFactory.getLogger(PruebaApplication.class);

    public static void ejemploDelayElements() throws Exception {
        Flux<Integer> rango = Flux.range(1, 12)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> log.info(i.toString()));

        //Imprimir hasta el ultimo
        // (Esto bloquea el flujo por cada elemento que tenga hasta el ultimo)
        rango.blockLast();
    }

    public static void ejemploInterval() throws Exception {
        Flux<Integer> rango = Flux.range(1, 12);
        Flux<Long> retraso = Flux.interval(Duration.ofSeconds(1));

        //Combina ambos flujos pero el que queremos es el rango
        rango.zipWith(retraso, (ra,re) -> ra)
                .doOnNext(i -> log.info(i.toString()))
                .blockLast();
    }

}
