package br.com.pedidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pedidos.entity.Item;
import br.com.pedidos.entity.Pedido;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	
	List<Item> findByPedido(Pedido pedido);		

}
