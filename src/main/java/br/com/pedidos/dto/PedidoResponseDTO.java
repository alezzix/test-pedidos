package br.com.pedidos.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class PedidoResponseDTO {

	private Long pedidoId;

	private Long clienteId;
	
	private BigDecimal imposto;

	private List<ItemDTO> itens;
	
	private String status;

}
