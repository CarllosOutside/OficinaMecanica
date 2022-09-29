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

import com.projeto.oficina.Cnpj;

//TABELA PESSOA JURIDICA
@SuppressWarnings("serial")
@Entity
@Table(name = "juridica")
@IdClass(JuridicaId.class)
public class Juridica implements Serializable{
	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long id;
	//COLUNAS
		@Id //PK e FK
		@OneToOne //UMA PESSOA JURIDICA PODE SER ASSOCIAD A À UMA PESSOA, E VICE-VERSA
		@JoinColumn(name = "cod_pessoa") //COLUNA cod_pessoa DA TABELA PESSOA
		private Pessoa cod_pessoa; //TABELA PESSOA -> A CHAVE SECUNDARIA cod_pessoa FICA ARMAZENADA NA TABELA JURIDICA
		//PARA EXISTIR PESSOA JURIDICA, DEVE EXISTIR PESSOA
		
		@Column
		@Cnpj
		@NotNull(message = "Pessoa jurídica deve possuir cnpj")
		String cnpj;
		
		
		//CONSTRUCTOR
		public Juridica() {}
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
