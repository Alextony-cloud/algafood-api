package io.github.alextonycloud.algafood.domain.repository;

import java.util.List;

import io.github.alextonycloud.algafood.domain.model.Permissao;

public interface PermissaoRepository {
	
	public List<Permissao> listar();
	
	public Permissao salvar(Permissao permissao) ;
	
	public Permissao buscar(Long id);
	
	public void remover(Permissao permissao);

}
