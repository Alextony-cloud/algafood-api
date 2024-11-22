package io.github.alextonycloud.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import io.github.alextonycloud.algafood.domain.model.Restaurante;
import io.github.alextonycloud.algafood.domain.repository.RestauranteRepository;


@Component
public class RestauranteRepositoryImpl implements RestauranteRepository{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> listar() {
		return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
	}

	@Override
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		return manager.merge(restaurante);
	}

	@Override
	public Restaurante buscar(Long id) {
		return manager.find(Restaurante.class, id);
	}

	@Override
	@Transactional
	public void remover(Restaurante restaurante) {
		restaurante = buscar(restaurante.getId());
		manager.remove(restaurante);
	}

}
