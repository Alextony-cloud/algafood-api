package io.github.alextonycloud.algafood.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.alextonycloud.algafood.domain.exception.EntidadeNaoEncontradaException;
import io.github.alextonycloud.algafood.domain.model.Cidade;
import io.github.alextonycloud.algafood.domain.service.CidadeService;

@RestController
@RequestMapping("api/cidades")
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;

	@GetMapping
	public ResponseEntity<List<Cidade>> listar() {
		List<Cidade> cidades = cidadeService.listar();
		return ResponseEntity.ok().body(cidades);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long id) {
		Cidade cidade = cidadeService.buscar(id);
		if (cidade != null) {
			return ResponseEntity.ok().body(cidade);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Cidade> inserir(@RequestBody Cidade cidade) {
		cidadeService.salvar(cidade);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(cidade.getId())
				.toUri();
		return ResponseEntity.created(uri).body(cidade);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@RequestBody Cidade cidade, @PathVariable Long id) {
		try {
			Cidade cidadeAtual = cidadeService.buscar(id);
			if (cidadeAtual != null) {
				BeanUtils.copyProperties(cidade, cidadeAtual, "id");
				cidadeService.salvar(cidadeAtual);
				return ResponseEntity.ok().body(cidadeAtual);
			} else {
				return ResponseEntity.notFound().build();
			}

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
}
