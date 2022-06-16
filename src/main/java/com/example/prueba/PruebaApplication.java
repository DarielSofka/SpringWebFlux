package com.example.prueba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class PruebaApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(PruebaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PruebaApplication.class, args);
    }

    public void run(String... args) throws Exception {

        Flux<String> nombres = Flux.just("Andres", "Pedro", "", "Diego", "Juan")
                .doOnNext(e -> {

                    if (e.isEmpty())
                        throw new RuntimeException("Nombres no pueden ser vacios");

                    System.out.println(e);
                });

        //Realizar el Flux
        nombres.subscribe(e -> log.info(e), error -> log.error(error.getMessage()));
    }
}
