package com.projeto.oficina.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OrdemServicoId implements Serializable{
	private long id; 
	private long codFuncionario;
	private String placa;
	
	OrdemServicoId() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCodFuncionario() {
		return codFuncionario;
	}

	public void setCodFuncionario(long codFuncionario) {
		this.codFuncionario = codFuncionario;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public OrdemServicoId(long id, long codFuncionario, String placa) {
		super();
		this.id = id;
		this.codFuncionario = codFuncionario;
		this.placa = placa;
	}
	
	
}
