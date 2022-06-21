package com.example.apiWebFlux.Router;

import com.example.apiWebFlux.Handler.ProductorHandler;
import com.example.apiWebFlux.Services.ProductoService;
import com.example.apiWebFlux.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(ProductorHandler handler){
        return route(GET("/api/v2").or(GET("/api/v3")), request -> handler.listar(request));
    }

}

