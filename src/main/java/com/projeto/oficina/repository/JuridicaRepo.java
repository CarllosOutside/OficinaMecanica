package com.projeto.oficina.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.oficina.model.Juridica;
import com.projeto.oficina.model.JuridicaId;

public interface JuridicaRepo extends JpaRepository<Juridica, JuridicaId> {
	Optional<Juridica> findByCnpj(String cnpj); 
}