package br.com.pedidos.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "t_pedido")
public class Pedido {

	@Id
	// @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private LocalDateTime dataHoraPedido;

	@ManyToOne
	private Cliente cliente;
	
	private String status;


}
