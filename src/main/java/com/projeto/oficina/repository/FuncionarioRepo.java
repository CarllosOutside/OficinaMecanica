package com.projeto.oficina.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projeto.oficina.model.Funcionario;
import com.projeto.oficina.model.Pessoa;

public interface FuncionarioRepo extends JpaRepository<Funcionario, Long> {

	Optional<Funcionario> findByPessoa(Pessoa pessoa);
	Page<Funcionario> findByPessoa(Pessoa pessoa, Pageable pageable);
	
	Page<Funcionario> findAllByPessoaNomeContaining(String nome, Pageable pageable);
}
