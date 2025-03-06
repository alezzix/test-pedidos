package br.com.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.pedidos.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	

	
	@Modifying
	@Transactional
	void deleteByNome(String nome);

}
