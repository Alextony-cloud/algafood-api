package io.github.alextonycloud.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import io.github.alextonycloud.algafood.domain.exception.EntidadeEmUsoException;
import io.github.alextonycloud.algafood.domain.exception.EntidadeNaoEncontradaException;
import io.github.alextonycloud.algafood.domain.model.Cidade;
import io.github.alextonycloud.algafood.domain.model.Estado;
import io.github.alextonycloud.algafood.domain.repository.CidadeRepository;
import io.github.alextonycloud.algafood.domain.repository.EstadoRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}

	public Cidade buscar(Long id) {
		return cidadeRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe cadastro de cidade com código %d", id)));
	}

	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.findById(estadoId).orElseThrow(()-> new EntidadeNaoEncontradaException(
				String.format("Não existe cadastro de estado com código %d", estadoId)));
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}

	public void deletar(Long id) {
		try {
			if (!cidadeRepository.existsById(id)) {
				throw new EntidadeNaoEncontradaException(
						String.format("Não existe um cadastro de cozinha com código %d", id));
			}
			cidadeRepository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d não pode ser removida, pois está em uso", id));
		}
	}
}
