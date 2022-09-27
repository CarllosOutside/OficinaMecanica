package com.projeto.oficina.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "regions")
public class Regiao {

	//COLUNAS
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	@Column
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public Regiao(String name) {
		super();
		this.name = name;
	}
	public Regiao() {}
}
