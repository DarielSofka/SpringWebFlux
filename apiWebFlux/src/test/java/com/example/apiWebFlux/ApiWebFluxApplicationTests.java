package com.example.apiWebFlux;

import com.example.apiWebFlux.Services.ProductoService;
import com.example.apiWebFlux.models.Categoria;
import com.example.apiWebFlux.models.Producto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class  ApiWebFluxApplicationTests {

	@Autowired
	private WebTestClient client;

	@Autowired
	private ProductoService service;


	@Test
	public void listarTest(){
		client.get()
				.uri("/api/v2/productos")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBodyList(Producto.class)
				.consumeWith(response -> {  //Para realizar mas verificaciones en los productos
					List<Producto> productos = response.getResponseBody();
					productos.forEach(p -> {
						System.out.println(p.getNombre());
					});
					Assertions.assertThat(productos.size() > 0).isTrue();
				});

		//.hasSize(9);
	}
	

	//Las pruebas unitarias no se pueden realizar adentro de un observable
	//Por ende se deben trabajar de forma sincrona (bloqueante)
	@Test
	public void verDetalle(){
		Producto producto = service.findByNombre("TV Panasonic Pantalla LCD").block();

		client.get()
				.uri("/api/v2/productos/{id}", Collections.singletonMap("id",producto.getId()))
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody(Producto.class)
				.consumeWith(response -> {
					Producto p = response.getResponseBody();
					Assertions.assertThat(p.getId()).isNotEmpty();
					Assertions.assertThat(p.getNombre()).isEqualTo("TV Panasonic Pantalla LCD");
				});

	/*			.expectBody()
				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.nombre").isEqualTo("TV Panasonic Pantalla LCD");*/
	}


	@Test
	public void crearTest(){

		Categoria categoria = service.findCategoriaByNombre("Muebles").block();

		Producto producto = new Producto("Mesa comedor", 100.0, categoria);

		client.post()
				.uri("/api/v2/productos")
				.contentType(MediaType.APPLICATION_JSON_UTF8) //Seria el contenido que va a mandar a la BD (REQUEST)
				.accept(MediaType.APPLICATION_JSON_UTF8)  //El accept es del RESPOSNE (seria el tipo de la respuesta)
				.body(Mono.just(producto),Producto.class)
				.exchange()
				.expectStatus().isCreated() //Siempre tiene que ser Created cuando creamos un objeto
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody(Producto.class)
				.consumeWith(response -> {
					Producto p = response.getResponseBody();
					Assertions.assertThat(p.getId()).isNotEmpty();
					Assertions.assertThat(p.getNombre()).isEqualTo("Mesa comedor");
					Assertions.assertThat(p.getCategoria().getNombre()).isEqualTo("Muebles");
				});

/*				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.nombre").isEqualTo("Mesa comedor")
				.jsonPath("$.categoria.nombre").isEqualTo("Muebles");*/
	}


	@Test
	public void editarTest(){

		//Producto Existente
		Producto producto = service.findByNombre("Sony Notebook").block();
		Categoria categoria = service.findCategoriaByNombre("Electrónico").block();

		//Producto a Editar
		Producto productoEditado = new Producto("Asus Notebook", 700.0, categoria);

		client.put()
				.uri("/api/v2/productos/{id}", Collections.singletonMap("id",producto.getId()))
				.contentType(MediaType.APPLICATION_JSON_UTF8) //Seria el contenido que va a mandar a la BD (REQUEST)
				.accept(MediaType.APPLICATION_JSON_UTF8)  //El accept es del RESPOSNE (seria el tipo de la respuesta)
				.body(Mono.just(productoEditado),Producto.class)
				.exchange()
				.expectStatus().isCreated() //Siempre tiene que ser Created cuando creamos un objeto
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.nombre").isEqualTo("Asus Notebook")
				.jsonPath("$.categoria.nombre").isEqualTo("Electrónico");
	}


	@Test
	public void eliminarTest(){

		Producto producto = service.findByNombre("Mica Cómoda 5 Cajones").block();

		client.delete()
				.uri("/api/v2/productos/{id}", Collections.singletonMap("id",producto.getId()))
				.exchange()
				.expectStatus().isNoContent()
				.expectBody()
				.isEmpty();

		client.get()
				.uri("/api/v2/productos/{id}", Collections.singletonMap("id",producto.getId()))
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.isEmpty();

	}

}
