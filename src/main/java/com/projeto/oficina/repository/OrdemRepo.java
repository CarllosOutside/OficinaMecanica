package com.projeto.oficina.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.oficina.model.OrdemServico;
import com.projeto.oficina.model.OrdemServicoId;

public interface OrdemRepo extends JpaRepository<OrdemServico, Long> {

	List<OrdemServico> findAllByDataAbertura(Date data);
Page<OrdemServico> findByPlaca(String placa, Pageable page);
Page<OrdemServico> findByCodFuncionario(Long codFuncionario, Pageable page);
List<OrdemServico> findAllByDataAberturaBetween(Date start,Date end);
OrdemServico findByDataAbertura(Date start);

}