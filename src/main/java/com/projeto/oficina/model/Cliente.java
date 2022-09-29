package com.projeto.oficina.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente {

	//COLUNAS
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long cod_cliente;
	
	@OneToOne
    @JoinColumn(name = "cod_pessoa")
	Pessoa pessoa;
	

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public long getCod_cliente() {
		return cod_cliente;
	}

	public Cliente() {}
	public Cliente(Pessoa pessoa) {
		this.pessoa = pessoa;
	} 
	
	
}
