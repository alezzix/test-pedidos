package br.com.pedidos.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.pedidos.dto.CriacaoResponseDTO;
import br.com.pedidos.dto.ItemDTO;
import br.com.pedidos.dto.PedidoRequestDTO;
import br.com.pedidos.entity.Cliente;
import br.com.pedidos.entity.Pedido;
import br.com.pedidos.entity.Produto;
import br.com.pedidos.repository.ClienteRepository;
import br.com.pedidos.repository.ItemRepository;
import br.com.pedidos.repository.PedidoRepository;
import br.com.pedidos.repository.ProdutoRepository;
import br.com.pedidos.services.PedidoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceImplTest {

	@InjectMocks
	private static PedidoServiceImpl service;
	@Mock
	private PedidoRepository pedidoRepository;
	@Mock
	private ClienteRepository clienteRepository;
	@Mock
	private ProdutoRepository produtoRepository;
	@Mock
	private ItemRepository itemRepository;

	@DisplayName("test criarPedido method from PedidoServiceImpl")
	@Test
	public void criarPedidoTest() {
		PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO();
		pedidoRequestDTO.setClienteId(1L);
		pedidoRequestDTO.setPedidoId(1L);

		List<ItemDTO> listItens = new ArrayList<ItemDTO>();
		ItemDTO item = new ItemDTO();
		item.setProdutoId(1L);
		item.setQuantidade(2);
		item.setValor(new BigDecimal(10));
		listItens.add(item);
		pedidoRequestDTO.setItens(listItens);

		Cliente cliente = new Cliente(1L, "Jose", "Endereco test");
		Optional<Cliente> clienteOpt = Optional.of((Cliente) cliente);

		Produto produto = new Produto(1L, "Prod Test", new BigDecimal(10));
		Optional<Produto> produtoOpt = Optional.of((Produto) produto);

		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setDataHoraPedido(LocalDateTime.now());
		pedido.setId(1L);
		pedido.setStatus("Criado");

		when(clienteRepository.findById(1L)).thenReturn(clienteOpt);
		when(produtoRepository.findById(1L)).thenReturn(produtoOpt);
		when(pedidoRepository.save(Mockito.any(Pedido.class))).thenReturn(pedido);

		CriacaoResponseDTO response = service.criarPedido(pedidoRequestDTO);

		assertEquals("Criado", response.getStatus());

	}

}
