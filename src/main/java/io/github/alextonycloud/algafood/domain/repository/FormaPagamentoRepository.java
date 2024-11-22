package io.github.alextonycloud.algafood.domain.repository;

import java.util.List;
import io.github.alextonycloud.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {
	
	public List<FormaPagamento> listar();
	
	public FormaPagamento salvar(FormaPagamento formaPagamento) ;
	
	public FormaPagamento buscar(Long id);
	
	public void remover(FormaPagamento formaPagamento);

}
