package io.github.alextonycloud.algafood.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilderFactory;

import io.github.alextonycloud.algafood.domain.exception.EntidadeNaoEncontradaException;
import io.github.alextonycloud.algafood.domain.model.Cidade;
import io.github.alextonycloud.algafood.domain.model.Cozinha;
import io.github.alextonycloud.algafood.domain.model.Estado;
import io.github.alextonycloud.algafood.domain.repository.CozinhaRepository;
import io.github.alextonycloud.algafood.domain.repository.EstadoRepository;
import io.github.alextonycloud.algafood.domain.service.EstadoService;

@RestController
@RequestMapping("api/estados")
public class EstadoController {

	@Autowired
	private EstadoService estadoService;

	@GetMapping
	public ResponseEntity<List<Estado>> listar() {
		List<Estado> estado = estadoService.listar();
		return ResponseEntity.ok(estado);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Estado> buscar(@PathVariable Long id) {
		Estado estado = estadoService.buscar(id);
		if (estado != null) {
			return ResponseEntity.ok().body(estado);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping()
	public ResponseEntity<Estado> inserir(@RequestBody Estado estado) {
		estadoService.salvar(estado);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(estado.getId())
				.toUri();
		return ResponseEntity.created(uri).body(estado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Estado> atualizar(@RequestBody Estado estado, @PathVariable Long id) {
		Estado estadoAtual = estadoService.buscar(id);
		if (estadoAtual != null) {
			BeanUtils.copyProperties(estado, estadoAtual, "id");
			estadoService.salvar(estadoAtual);
			return ResponseEntity.accepted().body(estadoAtual);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Cidade> deletar(@PathVariable Long id) {
		try {
			estadoService.deletar(id);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();

		}

	}
}
