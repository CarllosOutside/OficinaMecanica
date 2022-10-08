package com.projeto.oficina.model;


import java.io.Serializable;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.projeto.oficina.Cpf;

//TABELA PESSOA FISICA
@Entity
@Table(name = "fisica")
@IdClass(FisicaId.class)
public class Fisica implements Serializable{
	 private static final long serialVersionUID = -909206262878526790L; 
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	 
	@Id //PK e FK
	@Column(name = "cod_pessoa")
	private long codPessoa;
	
	@OneToOne
	@JoinColumn(name = "cod_pessoa", insertable = false, updatable = false) //junta à tabela acima
	private Pessoa pessoa; // é preciso haver uma pessoa
	
	
	@Column
	@Cpf
	@NotNull(message = "Pessoa física deve possuir cpf")
	String cpf;


	//CONSTRUCTOR
	public Fisica() {}
	

	public long getId() {
		return id;
	}


	public Fisica(long codPessoa, @NotNull(message = "Pessoa física deve possuir cpf") String cpf) {
		this.codPessoa = codPessoa;
		this.cpf = cpf;
	}


	public Fisica(long codPessoa, Pessoa pessoa, @NotNull(message = "Pessoa física deve possuir cpf") String cpf) {
		this.codPessoa = codPessoa;
		this.pessoa = pessoa;
		this.cpf = cpf;
	}


	public Pessoa getPessoa() {
		return pessoa;
	}


	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}


	public void setCodPessoa(long codPessoa) {
		this.codPessoa = codPessoa;
	}


	//GETTERS E SETTERS
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public long getCodPessoa() {
		return codPessoa;
	}
	
}
