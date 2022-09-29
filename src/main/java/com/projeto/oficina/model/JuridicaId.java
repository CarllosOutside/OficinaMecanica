package com.projeto.oficina.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JuridicaId implements Serializable{

	private Pessoa cod_pessoa;

	public Pessoa getCod_pessoa() {
		return cod_pessoa;
	}

	public void setCod_pessoa(Pessoa cod_pessoa) {
		this.cod_pessoa = cod_pessoa;
	}
	
}
