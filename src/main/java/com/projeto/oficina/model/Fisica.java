package com.projeto.oficina.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.projeto.oficina.Cpf;

//TABELA PESSOA FISICA
@Entity
@Table(name = "fisica")
public class Fisica {

	//COLUNAS
	@Id //PK e FK
	@OneToOne //UMA PESSOA FISICA PODE SER ASSOCIADA À UMA JURIDICA, E VICE-VERSA
	@JoinColumn(name = "cod_pessoa") //COLUNA cod_pessoa DA TABELA PESSOA
	Pessoa cod_pessoa; //TABELA PESSOA -> A CHAVE SECUNDARIA cod_pessoa FICA ARMAZENADA NA TABELA FISICA
	//PARA EXISTIR PESSOA FÍSICA, DEVE EXISTIR PESSOA
	
	@Column
	@Cpf
	String cpf;
}
