package com.projeto.oficina.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.oficina.model.Veiculo;

//ARMAZENA NA TABELA CIDADE, COM ID DO TIPO LONG
public interface VeiculoRepo extends JpaRepository<Veiculo, Long> {

}
