package io.github.alextonycloud.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import io.github.alextonycloud.algafood.domain.exception.EntidadeEmUsoException;
import io.github.alextonycloud.algafood.domain.exception.EntidadeNaoEncontradaException;
import io.github.alextonycloud.algafood.domain.model.Estado;
import io.github.alextonycloud.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public List<Estado> listar() {
		return estadoRepository.findAll();
	}

	public Estado buscar(Long id) {
		return estadoRepository.findById(id).orElseThrow(()-> new EntidadeNaoEncontradaException(
				String.format("Não existe cadastro de estado com código %d", id)));
	}

	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	public void deletar(Long id) {
		try {
			if (!estadoRepository.existsById(id)) {
				throw new EntidadeNaoEncontradaException(
						String.format("Não existe um cadastro de estado com código %d", id));
			}
			estadoRepository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Estado de código %d não pode ser removida, pois está em uso", id));
		}
	}

}
