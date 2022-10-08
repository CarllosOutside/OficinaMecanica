package com.projeto.oficina.model;

import java.io.Serializable;


@SuppressWarnings("serial")
public class FisicaId implements Serializable{

	private long codPessoa;
	private long id;
	
	public FisicaId(){}
	
	public FisicaId(long codPessoa, long id) {
		this.codPessoa = codPessoa;
		this.id = id;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getCodPessoa() {
		return codPessoa;
	}
	
	public void setCodPessoa(long codPessoa) {
		this.codPessoa = codPessoa;
	}
	

}
