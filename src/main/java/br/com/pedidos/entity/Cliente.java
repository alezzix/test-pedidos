package br.com.pedidos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_cliente")
public class Cliente {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String nome;
	private String endereco;
	
}
