package io.github.alextonycloud.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import io.github.alextonycloud.algafood.AlgafoodApiApplication;
import io.github.alextonycloud.algafood.domain.model.Cozinha;
import io.github.alextonycloud.algafood.domain.model.Restaurante;
import io.github.alextonycloud.algafood.domain.repository.CozinhaRepository;
import io.github.alextonycloud.algafood.domain.repository.RestauranteRepository;

public class RemocaoCozinhaMain  {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(1L);
		cadastroCozinha.remover(cozinha.getId());
	}
}
