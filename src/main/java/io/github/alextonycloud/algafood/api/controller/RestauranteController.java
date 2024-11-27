package io.github.alextonycloud.algafood.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.alextonycloud.algafood.domain.exception.EntidadeNaoEncontradaException;
import io.github.alextonycloud.algafood.domain.model.Restaurante;
import io.github.alextonycloud.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("api/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;

	@GetMapping
	public ResponseEntity<List<Restaurante>> listar() {
		List<Restaurante> restaurantes = restauranteService.listar();
		return ResponseEntity.ok().body(restaurantes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
		Restaurante restaurante = restauranteService.buscar(id);
		if (restaurante != null) {
			return ResponseEntity.ok().body(restaurante);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody Restaurante restaurante) {

		try {
			restaurante = restauranteService.salvar(restaurante);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
					.buildAndExpand(restaurante.getId()).toUri();
			return ResponseEntity.created(uri).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
