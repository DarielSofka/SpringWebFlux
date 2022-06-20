package com.example.prueba;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class EjemploContrapresion {

    private static final Logger log = LoggerFactory.getLogger(PruebaApplication.class);

    public static void ejemploContrapresion() throws Exception {

        //Ejemplo Corto
        /*Flux.range(1, 10)
                .log()
                .limitRate(3)
                .subscribe();
        */

        //Ejemplo Largo
        Flux.range(1, 10)
                .log()
                .subscribe(new Subscriber<Integer>() {
                   private Subscription s;

                   private Integer limite = 5;
                    private Integer consumido = 0;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.s = subscription;
                        s.request(limite);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log.info(integer.toString());
                        consumido++;
                        if(consumido == limite){
                            consumido = 0;
                            s.request(limite);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
