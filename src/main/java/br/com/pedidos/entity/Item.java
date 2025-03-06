package br.com.pedidos.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "t_item")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Integer quantidade;
	private BigDecimal valorTotalSemImpostos;
	private BigDecimal valorTotalDeImpostos;

	@ManyToOne
	private Pedido pedido;

	@ManyToOne
	private Produto produto;

}
