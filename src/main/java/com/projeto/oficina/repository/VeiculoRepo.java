package com.projeto.oficina.repository;


import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.oficina.model.Veiculo;

//ARMAZENA NA TABELA CIDADE, COM ID DO TIPO LONG
public interface VeiculoRepo extends JpaRepository<Veiculo, String> {
	List<Veiculo> findByMarcaContaining(String marca);
	Page<Veiculo> findAllByCodCliente(long codCliente, Pageable pageable);
	Page<Veiculo> findByCodClienteAndPlaca(long codCliente, String placa, Pageable pageable);
}
