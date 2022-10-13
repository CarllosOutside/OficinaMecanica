package com.projeto.oficina.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.oficina.model.Servico;


public interface ServicoRepo extends JpaRepository<Servico, Long> {
	Page<Servico> findByCodOrdem(Long codOrdem, Pageable page);
}
