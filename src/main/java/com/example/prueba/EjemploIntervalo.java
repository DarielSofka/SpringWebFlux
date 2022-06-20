package com.example.prueba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

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

    public static void ejemploIntervalo() throws Exception {
        Flux<Integer> rango = Flux.range(1, 12);
        Flux<Long> retraso = Flux.interval(Duration.ofSeconds(1));

        //Combina ambos flujos pero el que queremos es el rango
        rango.zipWith(retraso, (ra,re) -> ra)
                .doOnNext(i -> log.info(i.toString()))
                .blockLast();
    }

    public static void ejemploIntervaloInfinito() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.interval(Duration.ofSeconds(1))
                .doOnTerminate(latch::countDown)
                .flatMap(i -> {
                    if(i >= 5)
                        return Flux.error(new Exception("Solo hasta 5!"));
                    return Flux.just(i);
                })
                .map(i -> "Hola"+i)
                .retry(2)
                .subscribe(s -> log.info(s), e -> log.error(e.getMessage()));

        latch.await();
    }

}
