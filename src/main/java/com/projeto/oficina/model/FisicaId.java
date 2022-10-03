package com.projeto.oficina.model;

import java.io.Serializable;


@SuppressWarnings("serial")
public class FisicaId implements Serializable{

	private long cod_pessoa;
	private long id;
	
	public FisicaId(){}
	
	public FisicaId(long cod_pessoa, long id) {
		this.cod_pessoa = cod_pessoa;
		this.id = id;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getCod_pessoa() {
		return cod_pessoa;
	}
	
	public void setCod_pessoa(long cod_pessoa) {
		this.cod_pessoa = cod_pessoa;
	}
	

}
