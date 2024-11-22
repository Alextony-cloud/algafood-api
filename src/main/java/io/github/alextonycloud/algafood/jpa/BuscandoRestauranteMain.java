package io.github.alextonycloud.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import io.github.alextonycloud.algafood.AlgafoodApiApplication;
import io.github.alextonycloud.algafood.domain.model.Cozinha;
import io.github.alextonycloud.algafood.domain.model.Restaurante;
import io.github.alextonycloud.algafood.domain.repository.CozinhaRepository;
import io.github.alextonycloud.algafood.domain.repository.RestauranteRepository;

public class BuscandoRestauranteMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
		
		Restaurante restaurante = new Restaurante();
		restaurante.setId(1L);
		System.out.println(restauranteRepository.buscar(restaurante.getId()));
	}
}
