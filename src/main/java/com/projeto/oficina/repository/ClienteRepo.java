package com.projeto.oficina.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.oficina.model.Cliente;
import com.projeto.oficina.model.Pessoa;

public interface ClienteRepo extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByPessoa(Pessoa pessoa);
}