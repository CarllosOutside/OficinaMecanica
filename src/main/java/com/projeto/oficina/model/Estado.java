package com.projeto.oficina.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
//TABELA ESTADO
@Entity
@Table(name = "estado")
public class Estado {
	
	//COLUNAS
	@Id
    private long uf;
	
	@Column
    private String nome;

	//CONSTRUCTOR
	public Estado(String nome, long uf) {
		this.nome = nome;
		this.uf = uf;
	}
	
	//GETTERS E SETTERS
	public long getUf() {
		return uf;
	}

	public void setUf(long uf) {
		this.uf = uf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
