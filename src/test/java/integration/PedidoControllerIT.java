package integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.pedidos.ExecuteApplication;
import br.com.pedidos.dto.CriacaoResponseDTO;
import br.com.pedidos.dto.PedidoResponseDTO;
import utils.RequestsUtils;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ExecuteApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class PedidoControllerIT {

	@Value(value = "${local.server.port}")
	private int randomServerPort;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	@Order(1)
	@DisplayName("201 - test criarPedido")
	public void criarPedido201() {

		String resource = RequestsUtils.getURL(randomServerPort);
		URI uri = RequestsUtils.getDefaultURI(resource);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// restTemplate = new RestTemplate();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<String>(
				"{\"pedidoId\":1,\"clienteId\":1,\"itens\":[{\"produtoId\":1,\"quantidade\":2,\"valor\":52.70}]}",
				headers);

		CriacaoResponseDTO response = restTemplate.postForObject(uri + "/criar-pedido", request,
				CriacaoResponseDTO.class);

		assertThat("Criado", is(response.getStatus()));

	}

	@Test
	@Order(2)
	@DisplayName("200 - test getById")
	public void getById200() {

		String resource = RequestsUtils.getURL(randomServerPort);
		URI uri = RequestsUtils.getDefaultURI(resource);

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);

		ResponseEntity<PedidoResponseDTO> response = restTemplate.exchange(uri + "/get-by-id?idPedido={idPedido}",
				HttpMethod.GET, httpEntity, new ParameterizedTypeReference<PedidoResponseDTO>() {
				}, 1);

		assertThat(HttpStatus.OK, is(response.getStatusCode()));

	}

	@Test
	@Order(3)
	@DisplayName("200 - test getByStatus")
	public void getByStatus200() {

		String resource = RequestsUtils.getURL(randomServerPort);
		URI uri = RequestsUtils.getDefaultURI(resource);

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);

		ResponseEntity<List<PedidoResponseDTO>> response = restTemplate.exchange(
				uri + "/get-by-status?status={status}", HttpMethod.GET, httpEntity,
				new ParameterizedTypeReference<List<PedidoResponseDTO>>() {
				}, "Criado");

		assertThat(HttpStatus.OK, is(response.getStatusCode()));

	}

}
