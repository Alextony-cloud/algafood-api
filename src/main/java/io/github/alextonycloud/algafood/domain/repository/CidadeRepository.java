package io.github.alextonycloud.algafood.domain.repository;

import java.util.List;
import io.github.alextonycloud.algafood.domain.model.Cidade;

public interface CidadeRepository {
	
	public List<Cidade> listar();
	
	public Cidade salvar(Cidade cidade) ;
	
	public Cidade buscar(Long id);
	
	public void remover(Cidade cidade);

}
