package br.com.pedidos.dto;




import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class CriacaoResponseDTO {

	private Long id;

	private String status;
	
	//@Transient//jakarta
	//@Transient //java beans nao pode
	@JsonIgnore
	private String statusErro; 

}
