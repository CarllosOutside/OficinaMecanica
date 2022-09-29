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
	//COLUNAS
	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long id;
	 
	@Id //PK e FK
	@OneToOne
	@JoinColumn(name = "cod_pessoa") //COLUNA cod_pessoa DA TABELA PESSOA
	private Pessoa cod_pessoa; //TABELA PESSOA -> A CHAVE SECUNDARIA cod_pessoa FICA ARMAZENADA NA TABELA FISICA
	//PARA EXISTIR PESSOA FÍSICA, DEVE EXISTIR PESSOA
	
	
	@Column
	@Cpf
	@NotNull(message = "Pessoa física deve possuir cpf")
	String cpf;
	
	//CONSTRUCTOR
	public Fisica() {}
	

	public Fisica(Pessoa cod_pessoa, @NotNull(message = "Pessoa física deve possuir cpf") String cpf) {
		this.cod_pessoa = cod_pessoa;
		this.cpf = cpf;
	}


	//GETTERS E SETTERS
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Pessoa getCod_pessoa() {
		return cod_pessoa;
	}
	
}
