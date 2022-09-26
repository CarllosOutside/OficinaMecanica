package com.projeto.oficina.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.oficina.model.Cidade;

//ARMAZENA NA TABELA CIDADE, COM ID DO TIPO LONG
public interface CidadeRepo extends JpaRepository<Cidade, Long> {

	//RETORNA LISTA DE CIDADES POR NOME
	List<Cidade> findByNomeContaining(String nome);
}