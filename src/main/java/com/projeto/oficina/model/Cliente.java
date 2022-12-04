package com.projeto.oficina.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cliente")
public class Cliente {

	//COLUNAS
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long cod_cliente;
	
	@NotNull
	@OneToOne
    @JoinColumn(name = "cod_pessoa")
	Pessoa pessoa;
	
	@Column(columnDefinition="boolean default 'true'")
	private boolean cAtivo;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public long getCod_cliente() {
		return cod_cliente;
	}

	public Cliente(@NotNull Pessoa pessoa, boolean cAtivo) {
		super();
		this.pessoa = pessoa;
		this.cAtivo = cAtivo;
	}

	public Cliente() {}
	public Cliente(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public boolean iscAtivo() {
		return cAtivo;
	}

	public void setcAtivo(boolean cAtivo) {
		this.cAtivo = cAtivo;
	} 
	
	
	
}
