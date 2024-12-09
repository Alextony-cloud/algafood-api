package io.github.alextonycloud.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.alextonycloud.algafood.domain.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
}
