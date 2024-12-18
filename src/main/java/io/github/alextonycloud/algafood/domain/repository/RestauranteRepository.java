package io.github.alextonycloud.algafood.domain.repository;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.alextonycloud.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{
	

	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> buscarPorNome(String nome,@Param("id") Long cozinha);

	
	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);
	
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	
	int countByCozinhaId(Long cozinha);
}
