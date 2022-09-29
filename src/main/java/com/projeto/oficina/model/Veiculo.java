package com.projeto.oficina.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="veiculo")
public class Veiculo {
	
	@Id
	private String placa;
	
	@Column
	private String marca;

	@Column
	private String modelo;
	
	@Column
	private String ano;
	
	@Column
	private String cor;
	
	@ManyToOne //foreign key
    @JoinColumn(name = "cod_cliente")
	private Cliente cliente;
	
	public Veiculo(String placa, String marca, String modelo, String ano, String cor, Cliente cliente) {
		this.placa = placa;
		this.marca = marca;
		this.modelo = modelo;
		this.ano = ano;
		this.cor = cor;
		this.cliente = cliente;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public Veiculo(){}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Veiculo(String placa) {
		this.placa = placa;
	}
	
	
}
