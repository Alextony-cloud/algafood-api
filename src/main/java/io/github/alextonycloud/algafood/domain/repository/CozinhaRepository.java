package io.github.alextonycloud.algafood.domain.repository;

import java.util.List;

import io.github.alextonycloud.algafood.domain.model.Cozinha;

public interface CozinhaRepository {
	
	public List<Cozinha> listar();
	
	public Cozinha salvar(Cozinha cozinha) ;
	
	public Cozinha buscar(Long id);
	
	public void remover(Cozinha cozinha);

}
