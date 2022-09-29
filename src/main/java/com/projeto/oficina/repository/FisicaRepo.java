package com.projeto.oficina.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.oficina.model.Fisica;
import com.projeto.oficina.model.FisicaId;
import com.projeto.oficina.model.Pessoa;

public interface FisicaRepo extends JpaRepository<Fisica, FisicaId> {

	Optional<Fisica> findByCpf(String cpf); 
}