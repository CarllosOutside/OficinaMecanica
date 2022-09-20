package com.projeto.oficina.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cidade")
public class Cidade {
	
		//COLUNAS
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long cod_cidade;
		
		@Column
	    private String nome;
		
		@Column
		@ManyToOne
	    @JoinColumn(name = "uf")
		private Estado estado;

		//CONSTRUCTOR
		public Cidade(String nome, Estado estado) {
			this.nome = nome;
			this.estado = estado;
		}
		
		//GETTERS E SETTERS
		public String getNome() {
			return nome;
		}


		public void setNome(String nome) {
			this.nome = nome;
		}

		public Estado getEstado() {
			return estado;
		}

		public void setEstado(Estado estado) {
			this.estado = estado;
		}

		public long getCod_cidade() {
			return cod_cidade;
		}
		
		

}
