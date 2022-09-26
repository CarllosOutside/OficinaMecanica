package com.projeto.oficina.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//TABELA PESSOA
@Entity
@Table(name = "pessoa")
public class Pessoa {
	//COLUNAS
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cod_pessoa;

    @Column
    private String nome;

    @Column
    private String endereco;

    @Column
    private String telefone;

	//CHAVE SECUNDARIA
    @ManyToOne
    @JoinColumn(name = "cod_cidade") //COLUNA COD_CIDADE DA TABELA CIDADE
    Cidade cidade; //TABELA CIDADE

    public Pessoa() {}
    
    //CONSTRUCTOR
    public Pessoa(String nome, String endereco, String telefone, Cidade cidade) {
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.cidade = cidade;
	}
    
    //GETTERS E SETTERS
    public long getCod_pessoa() {
		return cod_pessoa;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Cidade getCidade() {
		return cidade;
	}
}
