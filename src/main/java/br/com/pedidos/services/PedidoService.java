package br.com.pedidos.services;

import java.util.List;

import br.com.pedidos.dto.CriacaoResponseDTO;
import br.com.pedidos.dto.PedidoRequestDTO;
import br.com.pedidos.dto.PedidoResponseDTO;

public interface PedidoService {
	
	CriacaoResponseDTO criarPedido(PedidoRequestDTO pedidoRequestDTO);
	
	PedidoResponseDTO getById(Long idPedido);
	
	List<PedidoResponseDTO> getByStatus(String status);

}
