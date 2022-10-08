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
	 
	 @Id
	 @Column(name = "cod_pessoa")
	 private long cod_pessoa;

	 @OneToOne
		@JoinColumn(name = "cod_pessoa", insertable = false, updatable = false) //junta à tabela acima
		private Pessoa pessoa; //TABELA PESSOA -> A CHAVE SECUNDARIA cod_pessoa FICA ARMAZENADA NA TABELA JURIDICA
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
		public long getCod_pessoa() {
			return cod_pessoa;
		}
		public void setCod_pessoa(long cod_pessoa) {
			this.cod_pessoa = cod_pessoa;
		}
		public Pessoa getPessoa() {
			return pessoa;
		}
		public void setPessoa(Pessoa pessoa) {
			this.pessoa = pessoa;
		}
		public long getId() {
			return id;
		}
		public Juridica(long cod_pessoa, Pessoa pessoa,
				@NotNull(message = "Pessoa jurídica deve possuir cnpj") String cnpj) {
			super();
			this.cod_pessoa = cod_pessoa;
			this.pessoa = pessoa;
			this.cnpj = cnpj;
		}
		public Juridica(long cod_pessoa, @NotNull(message = "Pessoa jurídica deve possuir cnpj") String cnpj) {
			this.cod_pessoa = cod_pessoa;
			this.cnpj = cnpj;
		}

}
