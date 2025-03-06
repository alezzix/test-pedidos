package br.com.pedidos.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.pedidos.dto.CriacaoResponseDTO;
import br.com.pedidos.dto.ItemDTO;
import br.com.pedidos.dto.PedidoRequestDTO;
import br.com.pedidos.dto.PedidoResponseDTO;
import br.com.pedidos.entity.Cliente;
import br.com.pedidos.entity.Item;
import br.com.pedidos.entity.Pedido;
import br.com.pedidos.entity.Produto;
import br.com.pedidos.repository.ClienteRepository;
import br.com.pedidos.repository.ItemRepository;
import br.com.pedidos.repository.PedidoRepository;
import br.com.pedidos.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

	private final PedidoRepository pedidoRepository;
	private final ClienteRepository clienteRepository;
	private final ProdutoRepository produtoRepository;
	private final ItemRepository itemRepository;

	@Value("${calculo.reforma.tributaria}")
	private boolean reformaTributaria;

	@Override
	public CriacaoResponseDTO criarPedido(PedidoRequestDTO pedidoRequestDTO) {
		log.info("Inicio criarPedido, pedido id:" + pedidoRequestDTO.getPedidoId());

		Pedido pedido = new Pedido();

		CriacaoResponseDTO criacaoResponseDTO = new CriacaoResponseDTO();

		pedido = this.salvaPedido(pedido, pedidoRequestDTO, criacaoResponseDTO);

		criacaoResponseDTO.setId(pedidoRequestDTO.getPedidoId());

		if (pedido == null) {
			criacaoResponseDTO.setStatus(criacaoResponseDTO.getStatusErro());
			return criacaoResponseDTO;
		}

		boolean salvaItensOK = this.salvaItensOK(pedido, pedidoRequestDTO, criacaoResponseDTO);

		if (salvaItensOK) {
			criacaoResponseDTO.setStatus("Criado");
		} else {
			criacaoResponseDTO.setStatus(criacaoResponseDTO.getStatusErro());
		}

		return criacaoResponseDTO;

	}

	private boolean salvaItensOK(Pedido pedido, PedidoRequestDTO pedidoRequestDTO,
			CriacaoResponseDTO criacaoResponseDTO) {
		log.info("Inicio salvaItens, pedido id:" + pedidoRequestDTO.getPedidoId());

		for (ItemDTO itemDTO : pedidoRequestDTO.getItens()) {

			Optional<Produto> produto = produtoRepository.findById(itemDTO.getProdutoId());

			if (!produto.isPresent()) {

				log.error("Produto com id" + itemDTO.getProdutoId() + " inexistente.");
				criacaoResponseDTO.setStatusErro("Produto com id" + itemDTO.getProdutoId() + " inexistente.");
				pedido.setStatus("Produto com id" + itemDTO.getProdutoId() + " inexistente.");
				pedidoRepository.save(pedido);
				return false;

			}

			BigDecimal valorTotalItens = new BigDecimal(0);
			Item item = new Item();

			item.setPedido(pedido);
			item.setProduto(produto.get());
			item.setQuantidade(itemDTO.getQuantidade());

			valorTotalItens = valorTotalItens.add(itemDTO.getValor().multiply(new BigDecimal(item.getQuantidade())));

			item.setValorTotalSemImpostos(valorTotalItens);

			item.setValorTotalDeImpostos(this.calculaImposto(valorTotalItens));
			itemRepository.save(item);

			produto = null;

			log.info("Fim salvaItens, pedido id: " + pedidoRequestDTO.getPedidoId());

		}
		return true;
	}

	private Pedido salvaPedido(Pedido pedido, PedidoRequestDTO pedidoRequestDTO,
			CriacaoResponseDTO criacaoResponseDTO) {
		log.info("Inicio salvaPedido, pedido id:" + pedidoRequestDTO.getPedidoId());
		if (pedidoRepository.findById(pedidoRequestDTO.getPedidoId()).isPresent()) {
			log.error("Pedido com id: " + pedidoRequestDTO.getPedidoId() + "duplicado.");
			criacaoResponseDTO.setStatusErro("Pedido com id: " + pedidoRequestDTO.getPedidoId() + " duplicado.");
			return null;

		}

		Optional<Cliente> cliente = clienteRepository.findById(pedidoRequestDTO.getClienteId());

		if (!cliente.isPresent()) {
			log.error("Cliente com id: " + pedidoRequestDTO.getClienteId() + " inexistente.");
			criacaoResponseDTO.setStatusErro("Cliente com id: " + pedidoRequestDTO.getClienteId() + " inexistente.");
			return null;

		}

		pedido.setCliente(cliente.get());
		pedido.setId(pedidoRequestDTO.getPedidoId());
		pedido.setDataHoraPedido(LocalDateTime.now());
		pedido.setStatus("Criado");

		pedido = pedidoRepository.save(pedido);
		log.info("Fim salvaPedido, pedido id:" + pedidoRequestDTO.getPedidoId());
		return pedido;

	}

	private BigDecimal calculaImposto(BigDecimal valorTotalItens) {
		BigDecimal total = null;
		if (this.reformaTributaria) {
			total = valorTotalItens.multiply(new BigDecimal(0.2));
		} else {
			total = valorTotalItens.multiply(new BigDecimal(0.3));
		}

		return total;
	}

	@Override
	public PedidoResponseDTO getById(Long idPedido) {
		Optional<Pedido> pedidoOpt = pedidoRepository.findById(idPedido);
		if (!pedidoOpt.isPresent()) {
			log.error("Pedido com id: " + idPedido + "inexistente.");
			return null;

		} else {
			PedidoResponseDTO pedidoResponseDTO = this.preenchePedidoResponse(pedidoOpt.get());

			return pedidoResponseDTO;
		}
	}

	@Override
	public List<PedidoResponseDTO> getByStatus(String status) {
		List<Pedido> pedidoEntityList = pedidoRepository.findByStatus(status);
		if (pedidoEntityList.isEmpty()) {
			log.error("Nenhum pedido com status: " + status + "encontrado.");
			return null;

		} else {
			List<PedidoResponseDTO> pedidoRespList = new ArrayList<PedidoResponseDTO>();

			for (Pedido entity : pedidoEntityList) {
				PedidoResponseDTO pedidoResponseDTO = this.preenchePedidoResponse(entity);
				pedidoRespList.add(pedidoResponseDTO);
			}

			return pedidoRespList;

		}
	}

	private PedidoResponseDTO preenchePedidoResponse(Pedido pedido) {
		PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO();
		BigDecimal valorTotalImpostos = new BigDecimal(0);
		List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();

		pedidoResponseDTO.setPedidoId(pedido.getId());
		pedidoResponseDTO.setClienteId(pedido.getCliente().getId());
		pedidoResponseDTO.setStatus(pedido.getStatus());

		List<Item> listItemEntity = itemRepository.findByPedido(pedido);
		if (!listItemEntity.isEmpty()) {

			for (Item i : listItemEntity) {
				valorTotalImpostos = valorTotalImpostos.add(i.getValorTotalDeImpostos());
				ItemDTO itemDTO = new ItemDTO();
				itemDTO.setProdutoId(i.getProduto().getId());
				itemDTO.setQuantidade(i.getQuantidade());
				itemDTO.setValor(i.getProduto().getValor());
				itemDTOList.add(itemDTO);
			}

			pedidoResponseDTO.setImposto(valorTotalImpostos);
			pedidoResponseDTO.setItens(itemDTOList);
		}
		return pedidoResponseDTO;

	}

}
