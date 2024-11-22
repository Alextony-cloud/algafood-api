package io.github.alextonycloud.algafood.domain.repository;

import java.util.List;
import io.github.alextonycloud.algafood.domain.model.Restaurante;

public interface RestauranteRepository {
	
	public List<Restaurante> listar();
	
	public Restaurante salvar(Restaurante restaurante) ;
	
	public Restaurante buscar(Long id);
	
	public void remover(Restaurante restaurante);

}
