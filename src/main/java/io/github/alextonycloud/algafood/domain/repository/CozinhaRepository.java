package io.github.alextonycloud.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.alextonycloud.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {
	
	List<Cozinha> findCozinhasByNomeContaining(String nome);
	
	Optional<Cozinha> findByNome(String nome);
	
	
}
