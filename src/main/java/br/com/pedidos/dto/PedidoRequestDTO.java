package br.com.pedidos.dto;

import java.util.List;

import lombok.Data;

@Data
public class PedidoRequestDTO {

	private Long pedidoId;

	private Long clienteId;

	private List<ItemDTO> itens;

}
