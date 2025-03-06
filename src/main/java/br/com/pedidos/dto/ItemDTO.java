package br.com.pedidos.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ItemDTO {

	private Long produtoId;

	private Integer quantidade;
	
	private BigDecimal valor;

}
