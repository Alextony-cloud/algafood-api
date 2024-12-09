package io.github.alextonycloud.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import io.github.alextonycloud.algafood.domain.exception.EntidadeEmUsoException;
import io.github.alextonycloud.algafood.domain.exception.EntidadeNaoEncontradaException;
import io.github.alextonycloud.algafood.domain.model.Cozinha;
import io.github.alextonycloud.algafood.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha salvar(Cozinha cozinha) {

		return cozinhaRepository.save(cozinha);
	}

	public void excluir(Long id) {
		try {
			if (!cozinhaRepository.existsById(id)) {
				throw new EntidadeNaoEncontradaException(
						String.format("Não existe um cadastro de cozinha com código %d", id));
			}
			cozinhaRepository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d não pode ser removida, pois está em uso", id));
		}
	}
}
