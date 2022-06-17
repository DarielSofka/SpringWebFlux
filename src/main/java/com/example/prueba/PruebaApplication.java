package com.example.prueba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static com.example.prueba.EjemploCollectList.ejemploCollectList;
import static com.example.prueba.EjemploFlatMap.ejemploFlatMap;
import static com.example.prueba.EjemploIterable.ejemploIterable;
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
        ejemploCollectList();
    }


}
