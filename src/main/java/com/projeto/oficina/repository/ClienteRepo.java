package com.projeto.oficina.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.projeto.oficina.model.Cliente;
import com.projeto.oficina.model.Pessoa;

public interface ClienteRepo extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByPessoa(Pessoa pessoa);
	Page<Cliente> findByPessoa(Pessoa pessoa, Pageable pageable);
	
	Page<Cliente> findAllByPessoaNomeContaining(String nome, Pageable pageable);
}