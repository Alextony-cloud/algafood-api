package io.github.alextonycloud.algafood.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.alextonycloud.algafood.domain.exception.EntidadeNaoEncontradaException;
import io.github.alextonycloud.algafood.domain.model.Restaurante;
import io.github.alextonycloud.algafood.domain.repository.RestauranteRepository;
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
	
	
	@GetMapping("/por-taxa-frete")
	public ResponseEntity<List<Restaurante>> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		List<Restaurante> restaurantes = restauranteService.buscarTaxaFrete(taxaInicial, taxaFinal);
		if (!restaurantes.isEmpty()) {
			return ResponseEntity.ok().body(restaurantes);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/por-nome")
	public ResponseEntity<List<Restaurante>> restaurantesPorTaxaFrete(String nome, Long id) {
		List<Restaurante> restaurantes = restauranteService.findByNomeContainingAndCozinhaId(nome, id);
		if (!restaurantes.isEmpty()) {
			return ResponseEntity.ok().body(restaurantes);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	@GetMapping("/por-primeiro-nome")
	public ResponseEntity<Optional<Restaurante>> buscarRestaurantesPorPrimeiroNome(String nome) {
		Optional<Restaurante> restaurante = restauranteService.buscarRestaurantesPorPrimeiroNome(nome);
		if (restaurante.isPresent()) {
			return ResponseEntity.ok().body(restaurante);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/count")
	public int countRestaurantePorCozinha(Long id) {
		return restauranteService.countRestaurantePorCozinha(id);
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

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@RequestBody Restaurante restaurante, @PathVariable Long id) {
		try {
			Restaurante restauranteAtual = restauranteService.buscar(id);
			if(restauranteAtual != null) {
				BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
				restauranteService.salvar(restauranteAtual);
				return ResponseEntity.ok(restauranteAtual);
			}
			return ResponseEntity.notFound().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizar(@RequestBody Map<String, Object> campos, @PathVariable Long id) {
		Restaurante restauranteAtual = restauranteService.buscar(id);
		if(restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}
		
		merge(campos, restauranteAtual);
		
		return atualizar(restauranteAtual, id);
	}

	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
		camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
}
