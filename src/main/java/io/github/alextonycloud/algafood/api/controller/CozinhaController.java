package io.github.alextonycloud.algafood.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;

import io.github.alextonycloud.algafood.api.model.CozinhasXmlWrapper;
import io.github.alextonycloud.algafood.domain.exception.EntidadeEmUsoException;
import io.github.alextonycloud.algafood.domain.exception.EntidadeNaoEncontradaException;
import io.github.alextonycloud.algafood.domain.model.Cozinha;
import io.github.alextonycloud.algafood.domain.repository.CozinhaRepository;
import io.github.alextonycloud.algafood.domain.service.CozinhaService;

@RestController
@RequestMapping(value = "api/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaService cozinhaService;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@GetMapping()
	public ResponseEntity<List<Cozinha>> listar() {
		List<Cozinha> cozinhas = cozinhaRepository.findAll();
		return ResponseEntity.ok().body(cozinhas);
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml() {
		return new CozinhasXmlWrapper(cozinhaRepository.findAll());
	}

	
	  @GetMapping("/por-nome") public ResponseEntity<List<Cozinha>> buscarCozinhasPorNome(@RequestParam("nome") String nome) { 
		  List<Cozinha> Cozinhas = cozinhaRepository.findCozinhasByNomeContaining(nome); 
		  return ResponseEntity.ok(Cozinhas); }
	 
	  @GetMapping("/unica/por-nome") public ResponseEntity<Cozinha> cozinhaPorNome(@RequestParam("nome") String nome) { 
		  Optional<Cozinha> Cozinha = cozinhaRepository.findByNome(nome); 
		  return ResponseEntity.ok(Cozinha.get()); 
	  }

	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
		Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
		if (cozinha.isPresent()) {
			return ResponseEntity.ok().body(cozinha.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Cozinha> inserir(@RequestBody Cozinha cozinha) {
		cozinhaService.salvar(cozinha);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(cozinha.getId())
				.toUri();
		return ResponseEntity.created(uri).body(cozinha);

	}

	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@RequestBody Cozinha cozinha, @PathVariable Long id) {
		Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(id);

		if (cozinhaAtual.isPresent()) {
			BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
			Cozinha cozinhaSalva = cozinhaService.salvar(cozinhaAtual.get());
			return ResponseEntity.accepted().body(cozinhaSalva);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		try {
			cozinhaService.excluir(id);
			return ResponseEntity.noContent().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
