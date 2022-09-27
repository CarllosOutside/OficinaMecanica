package com.projeto.oficina.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//TABELA ESTADO
@Entity
@Table(name = "states")
public class Estado {
	
	//COLUNAS
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	@Column
    private long uf_code; //UF É ID DE ESTADO
	
	@Column
    private String name;
	
	@Column
	private char uf[];
		//CONSTRAINT
	@ManyToOne
	@JoinColumn(name = "region_id")
	private Regiao region_id; 

	//CONSTRUCTORS
	public Estado() {}
	public Estado(long uf_code, String name, char[] uf, Regiao region_id) {
		this.uf_code = uf_code;
		this.name = name;
		this.uf = uf;
		this.region_id = region_id;
	}

	//GETTERS E SETTERS
	public long getUf_code() {
		return uf_code;
	}

	public long getId() {
		return id;
	}

	public char[] getUf() {
		return uf;
	}

	public void setUf(char[] uf) {
		this.uf = uf;
	}

	public Regiao getRegion_id() {
		return region_id;
	}

	public void setRegion_id(Regiao region_id) {
		this.region_id = region_id;
	}

	public void setUf_code(long uf) {
		this.uf_code = uf;
	}

	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}
}
