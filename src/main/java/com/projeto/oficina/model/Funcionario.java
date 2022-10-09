package com.projeto.oficina.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "funcionario")
public class Funcionario {

	//COLUNAS
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long cod_funcionario;
	
	@NotNull
	@OneToOne
    @JoinColumn(name = "cod_pessoa")
	Pessoa pessoa;
	

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public long getCod_funcionario() {
		return cod_funcionario;
	}

	public Funcionario() {}
	public Funcionario(Pessoa pessoa) {
		this.pessoa = pessoa;
	} 
	
	
}
