package com.projeto.oficina.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.oficina.model.Estado;

//ARMAZENA NA TABELA ESTADO, COM UF DO TIPO LONG
public interface EstadoRepo extends JpaRepository<Estado, Long> {

	//RETORNA LISTA DE ESTADOS POR NOME
	List<Estado> findByNameContaining(String nome);
}