package br.com.pedidos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedidos.dto.CriacaoResponseDTO;
import br.com.pedidos.dto.PedidoRequestDTO;
import br.com.pedidos.dto.PedidoResponseDTO;
import br.com.pedidos.services.PedidoService;

@RestController
@Validated
@RequestMapping("/v1/pedidos")

public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@PostMapping(value = "/criar-pedido", produces = { "application/json" })
	public ResponseEntity<CriacaoResponseDTO> criarPedido(@RequestBody PedidoRequestDTO pedido) {

		CriacaoResponseDTO criacaoResponseDTO = pedidoService.criarPedido(pedido);
		if (criacaoResponseDTO.getStatusErro() == null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(criacaoResponseDTO);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(criacaoResponseDTO);
		}

	}

	@GetMapping(value = "/get-by-id", produces = { "application/json" })
	public ResponseEntity<PedidoResponseDTO> getById(@RequestParam("idPedido") Long idPedido) {

		PedidoResponseDTO pedidoResponseDTO = pedidoService.getById(idPedido);

		if (pedidoResponseDTO != null) {
			return ResponseEntity.status(HttpStatus.OK).body(pedidoResponseDTO);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

	}

	@GetMapping(value = "/get-by-status", produces = { "application/json" })
	public ResponseEntity<List<PedidoResponseDTO>> getByStatus(@RequestParam("status") String status) {

		List<PedidoResponseDTO> pedidoResponseListDTO = pedidoService.getByStatus(status);

		if (pedidoResponseListDTO != null && !pedidoResponseListDTO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(pedidoResponseListDTO);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

	}

}
