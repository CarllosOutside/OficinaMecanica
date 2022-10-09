package com.projeto.oficina.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.oficina.model.OrdemServico;
import com.projeto.oficina.model.OrdemServicoId;

public interface OrdemRepo extends JpaRepository<OrdemServico, OrdemServicoId> {

	List<OrdemServico> findAllByDataAbertura(Date data);
Page<OrdemServico> findByPlaca(String placa, Pageable page);
Page<OrdemServico> findByCodFuncionario(long codFuncionario, Pageable page);
Optional<OrdemServico> findById(long id);
void deleteById(long id);

}