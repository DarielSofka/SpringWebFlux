package com.example.prueba;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class PruebaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PruebaApplication.class, args);
	}

	public void run(String... args) throws Exception {

		Flux<String> nombres = Flux.just("Andres","Pedro","Diego","Juan")
				.doOnNext(System.out::println);  //En cada elemento del Flux hacer esta operacion

		//Realizar el Flux
		nombres.subscribe();
	}
}
