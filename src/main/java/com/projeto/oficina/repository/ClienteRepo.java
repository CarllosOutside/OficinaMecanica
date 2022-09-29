package com.projeto.oficina.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.oficina.model.Cliente;

public interface ClienteRepo extends JpaRepository<Cliente, Long> {

	List<Cliente> findByNomeContaining(String nome);
}