package br.com.pedidos.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_produto")
public class Produto {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String nome;
	private BigDecimal valor;
	
}
