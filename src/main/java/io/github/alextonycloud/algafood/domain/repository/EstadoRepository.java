package io.github.alextonycloud.algafood.domain.repository;

import java.util.List;
import io.github.alextonycloud.algafood.domain.model.Estado;

public interface EstadoRepository {
	
	public List<Estado> listar();
	
	public Estado salvar(Estado estado) ;
	
	public Estado buscar(Long id);
	
	public void remover(Estado estado);

}
