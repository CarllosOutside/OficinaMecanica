package com.projeto.oficina.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.projeto.oficina.Cnpj;

//TABELA PESSOA JURIDICA
@SuppressWarnings("serial")
@Entity
@Table(name = "juridica")
public class Juridica implements Serializable{

	//COLUNAS
		@EmbeddedId //PK e FK
		@OneToOne //UMA PESSOA JURIDICA PODE SER ASSOCIAD A À UMA PESSOA, E VICE-VERSA
		@JoinColumn(name = "cod_pessoa") //COLUNA cod_pessoa DA TABELA PESSOA
		Pessoa cod_pessoa; //TABELA PESSOA -> A CHAVE SECUNDARIA cod_pessoa FICA ARMAZENADA NA TABELA JURIDICA
		//PARA EXISTIR PESSOA JURIDICA, DEVE EXISTIR PESSOA
		
		@Column
		@Cnpj
		String cnpj;
		
		
		//CONSTRUCTOR
		public Juridica(String cnpj) {
			this.cnpj = cnpj;
		}

		//COLUNAS
		public String getCnpj() {
			return cnpj;
		}

		public void setCnpj(String cnpj) {
			this.cnpj = cnpj;
		}

		public Pessoa getCod_pessoa() {
			return cod_pessoa;
		}
}
