package com.projeto.oficina.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JuridicaId implements Serializable{

	private long codPessoa;
	private long id;
	
	public JuridicaId() {}

	public long getCodPessoa() {
		return codPessoa;
	}

	public void setCodPessoa(long codPessoa) {
		this.codPessoa = codPessoa;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public JuridicaId(long codPessoa, long id) {
		this.codPessoa = codPessoa;
		this.id = id;
	}
	

	
	
}
