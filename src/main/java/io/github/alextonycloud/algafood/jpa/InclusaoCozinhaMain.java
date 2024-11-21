package io.github.alextonycloud.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import io.github.alextonycloud.algafood.AlgafoodApiApplication;
import io.github.alextonycloud.algafood.domain.model.Cozinha;
import io.github.alextonycloud.algafood.domain.repository.CozinhaRepository;

public class InclusaoCozinhaMain  {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
		
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Coreana");
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		
		cadastroCozinha.salvar(cozinha2);
		cadastroCozinha.salvar(cozinha1);
		
	}
}
