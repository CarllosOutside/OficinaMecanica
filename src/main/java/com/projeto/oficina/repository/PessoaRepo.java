package com.projeto.oficina.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.oficina.model.Pessoa;

//ARMAZENA NA TABELA PESSOA, COM COD_PESSOA DO TIPO LONG
public interface PessoaRepo extends JpaRepository<Pessoa, Long> {

	//RETORNA LISTA DE PESSOAS POR NOME
	//List<Pessoa> findByNomeContaining(String nome);
	Page<Pessoa> findByNomeContaining(String nome, Pageable pageable);
	List<Pessoa> findByNomeContaining(String nome);
}